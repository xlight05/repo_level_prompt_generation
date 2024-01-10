package edu.spbsu.eshop.storage;

import static edu.spbsu.eshop.hibernate.HibernateUtil.getEntityManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import edu.spbsu.eshop.beans.ErrorBean;
import edu.spbsu.eshop.storage.data.Category;
import edu.spbsu.eshop.storage.data.Comment;
import edu.spbsu.eshop.storage.data.Customer;
import edu.spbsu.eshop.storage.data.EShopPersistanceUnit;
import edu.spbsu.eshop.storage.data.Group;
import edu.spbsu.eshop.storage.data.Order;
import edu.spbsu.eshop.storage.data.Product;
import edu.spbsu.eshop.storage.exceptions.FileRecivingException;
import edu.spbsu.eshop.storage.exceptions.UserNotFoundException;

@SuppressWarnings("unchecked")
public class Storage {
    public static final String UPLOAD_DIR = "/home/eshop2/upload/";
    public static final String IMAGES_SUBDIR = "img";
    public static final String PRODUCTS_PHOTO_PREFIX = "pr_img";
    private static final String UNAUTHORIZED_USERS_GROUP_NAME = "Unauthorized";
    private static final String REGISTRED_USERS_GROUP_NAME = "Registered";

    /**
     * 
     * @param image
     *            uploaded image
     * @return saved image file name
     * @throws IOException
     * @throws FileRecivingException
     */
    public static String saveImage(UploadedFile image) throws IOException,
	    FileRecivingException {
	return FileSaver.savePhoto(image);
    }

    public static Customer getCustomer(String login)
	    throws UserNotFoundException {
	try {
	    Customer customer = (Customer) getQuerySingleResult(
		    "from Customer as customer where customer.login=?1", login);
	    return customer;
	} catch (NoResultException t) {
	    throw new UserNotFoundException(login);
	}
    }

    public static void persistUser(Customer user) {
	persist(user);
    }

    public static void persist(EShopPersistanceUnit... objects) {
	EntityManager em = getEntityManager();
	EntityTransaction transaction = em.getTransaction();
	transaction.begin();
	for (Object o : objects) {
	    em.persist(o);
	}
	transaction.commit();
	em.close();
    }

    public static List<Customer> getCustomers() {
	return getQueryResultList("from Customer");
    }

    public static List<Order> getOrders() {
	return getQueryResultList("from Order");
    }

    public static List<Order> getOrders(Customer customer) {
	return getQueryResultList(
		"from Order as order where order.customer=?1", customer);
    }

    public static Order getCurrentOrder(Customer customer) {
	Order result = null;
	EntityManager em = getEntityManager();
	EntityTransaction tr = em.getTransaction();
	tr.begin();
	/*
	 * if(customer==null) { Order order = new Order();
	 * order.setCustomer(customer); em.persist(order); result = order; }
	 */
	try {
	    Query query = em
		    .createQuery(
			    "from Order as order where order.state=?1 and order.customer=?2")
		    .setParameter(1, Order.State.Current).setParameter(2,
			    customer);
	    result = (Order) query.getSingleResult();
	    // TODO check if more than one current
	} catch (NoResultException e) {
	    Order order = new Order();
	    order.setCustomer(customer);
	    order.setState(Order.State.Current);
	    em.persist(order);
	    result = order;
	} finally {
	    tr.commit();
	    em.close();
	}
	return result;

    }

    public static List<Order> getOrdersButCurrent(Customer customer) {
	return getQueryResultList(
		"from Order as order where order.state!=?1 order.customer=?2",
		Order.State.Current, customer);
    }

    private static List getQueryResultList(String queryString, Object... params) {
	EntityManager em = getEntityManager();
	Query query = em.createQuery(queryString);
	for (int i = 1; i <= params.length; i++) {
	    query.setParameter(i, params[i - 1]);
	}
	List result = query.getResultList();
	em.close();
	return result;
    }

    private static Object getQuerySingleResult(String queryString,
	    Object... params) {
	EntityManager em = getEntityManager();
	Query query = em.createQuery(queryString);
	for (int i = 1; i <= params.length; i++) {
	    query.setParameter(i, params[i - 1]);
	}
	Object result = query.getSingleResult();
	em.close();
	return result;
    }

    public static Category[] getChildCategories(Category cat) {
	List cats;
	if (cat == null) {
	    cats = getQueryResultList("from Category as cat where cat.parent is null");
	} else {
	    cats = getQueryResultList(
		    "from Category as cat where cat.parent = ?1", cat);
	}

	return (Category[]) cats.toArray(new Category[0]);
    }

    public static Customer getCustomer(long id) {
	return (Customer) getQuerySingleResult(
		"from Customer as customer where customer.id=?1", id);

    }

    public static Category getCategory(long id) {
	return (Category) getQuerySingleResult(
		"from Category as cat where cat.id=?1", id);

    }

    public static List<Product> getProductsForCategory(Category category) {
	List<Product> result;
	EntityManager em = getEntityManager();
	EntityTransaction tr = em.getTransaction();
	tr.begin();
	{
	    if (category == null) {
		result = getQueryResultList("from Product");
	    } else {
		result = internalRecursiveGetCategorysProducts(category, em);
	    }

	}
	tr.commit();
	em.close();
	return result;
    }

    private static List<Product> internalRecursiveGetCategorysProducts(
	    Category cat, EntityManager em) {
	List<Product> list = new ArrayList<Product>();
	List<Category> childCats = em.createQuery(
		"from Category as cat where cat.parent = ?1").setParameter(1,
		cat).getResultList();
	List<Product> products = em.createQuery(
		"from Product as prod where prod.category = ?1").setParameter(
		1, cat).getResultList();
	list.addAll(products);

	if (childCats.isEmpty())
	    return list;

	for (Category c : childCats) {
	    list.addAll(internalRecursiveGetCategorysProducts(c, em));
	}
	return list;
    }

    public static Group[] getGroups() {

	List cats = getQueryResultList("from Group");

	return (Group[]) cats.toArray(new Group[0]);
    }

    public static Group getGroup(Long id) {
	return (Group) getQuerySingleResult(
		"from Group as group where group.id=?1", id);
    }

    public static void merge(Object obj) {
	EntityManager em = getEntityManager();
	EntityTransaction tr = em.getTransaction();
	tr.begin();
	em.merge(obj);
	tr.commit();
	em.close();
    }

    public static void removeCategoryCascade(Category cat) {
	EntityManager em = getEntityManager();
	EntityTransaction tr = em.getTransaction();
	tr.begin();
	removeCategory(cat, em);
	tr.commit();
	em.close();
    }

    private static void removeCategory(Category cat, EntityManager em) {
	List<Category> children = em.createQuery(
		"from Category as cat where cat.parent = ?1").setParameter(1,
		cat).getResultList();
	for (Category category : children) {
	    removeCategory(category, em);
	}
	remove(cat);
    }

    public static void remove(Object object) {
	EntityManager em = getEntityManager();
	EntityTransaction tr = em.getTransaction();
	tr.begin();
	em.remove(em.merge(object));
	tr.commit();
	em.close();
    }

    public static Product getProduct(Long id) {
	return (Product) getQuerySingleResult(
		"from Product as prod where prod.id=?1", id);
    }

    public static void removePhoto(String filename) {
	FileSaver.removeFile(filename, IMAGES_SUBDIR);
    }

    public static Object getOrder(Long id) {
	return getQuerySingleResult("from Order as ord where ord.id=?1", id);
    }

    public static List<Comment> getProductComments(Product product) {
	return getQueryResultList(
		"from Comment as comment where comment.product = ?1", product);
    }

    public static Group getGroupForUnauthorizes() {
	return getOrCreateGroupByName(UNAUTHORIZED_USERS_GROUP_NAME);
    }

    private static Group getOrCreateGroupByName(String name) {
	Group group;
	try {
	    group = (Group) Storage.getQuerySingleResult(
		    "from Group as group where group.name=?1", name);

	} catch (NoResultException e) {
	    group = new Group();
	    group.setName(name);
	    persist(group);
	}
	return group;
    }

    public static List<Product> searchProductsByName(String... words) {
	List<Product> result = new ArrayList<Product>();
	String query = "from Product as product where";
	Object[] queryWords = new Object[words.length];
	for (int i = 0; i < words.length - 1; i++) {
	    query += " product.name like ?" + (i + 1) + " and";
	    queryWords[i] = new String("%" + words[i] + "%");
	}
	queryWords[words.length - 1] = new String("%" + words[words.length - 1]
		+ "%");
	query += " product.name like ?" + (words.length);
	ErrorBean.setErrorMessage(query + queryWords[0] + words[0]);
	result = getQueryResultList(query, queryWords);
	return result;
    }

    public static void removeComment(Long id) {
	EntityManager em = getEntityManager();
	EntityTransaction tr = em.getTransaction();
	tr.begin();

	Comment comment = (Comment) em.createQuery(
		"from Comment as comment where comment.id=?1").setParameter(1,
		id).getSingleResult();
	em.remove(comment);

	tr.commit();
	em.close();
    }

    public static Group getGroupForRegistred() {
	return getOrCreateGroupByName(REGISTRED_USERS_GROUP_NAME);
    }
}
