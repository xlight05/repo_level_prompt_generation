package edu.spbsu.eshop.dao.impl;

import static edu.spbsu.eshop.hibernate.HibernateUtil.getEntityManager;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import edu.spbsu.eshop.dao.OrderDAO;
import edu.spbsu.eshop.storage.data.Customer;
import edu.spbsu.eshop.storage.data.Order;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public Order getLastConfirmed(Customer customer) {
	Order order = new Order();
	String getAllCustomerOrdersQueryString = "from Order as order where order.customer=?1";

	EntityManager entityManager = getEntityManager();
	Query query = entityManager
		.createQuery(getAllCustomerOrdersQueryString).setParameter(1,
			customer);
	List resultList = query.getResultList();
	entityManager.close();
	// TODO: may be add creation date column to ORDER table in database and
	// sort orders by it?
	for (int i = resultList.size() - 1; i >= 0; i--) {
	    order = (Order) resultList.get(i);
	    if (order.getState().equals(Order.State.Confirmed)) {
		return order;
	    }
	}
	return null;
    }

    /**
     * @author Vladimir Vasilev Don't use it please =)
     */
    @Override
    public Long getOrderProductAmount(Long orderId, Long productId) {
	Order order = new Order();
	Long counter = 0L;
	EntityManager entityManager = getEntityManager();
	try {
	    order = entityManager.find(Order.class, orderId);
	    counter = order.getProductAmount().get(productId);
	    return counter;
	} catch (NoResultException e) {
	    return null;
	}
    }

}
