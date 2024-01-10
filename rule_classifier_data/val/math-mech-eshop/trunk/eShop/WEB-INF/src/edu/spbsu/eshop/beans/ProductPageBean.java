package edu.spbsu.eshop.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import edu.spbsu.eshop.storage.Storage;
import edu.spbsu.eshop.storage.data.Comment;
import edu.spbsu.eshop.storage.data.Order;
import edu.spbsu.eshop.storage.data.Product;

public class ProductPageBean {

    private SessionBean userBean;

    private Product displayedProduct;

    private OrdersBean orders;

    private Comment comment = new Comment();

    public void setDisplayedProductId(Long id) {
	this.displayedProduct = Storage.getProduct(id);
    }

    public Product getDisplayedProduct() {
	return displayedProduct;
    }

    public List<Comment> getProductComments() {
	return Storage.getProductComments(displayedProduct);
    }
    
    /**
     * Finds displayed product in the current order.
     * @param order - the current order.
     * @return true if product exists in the current order, false otherwise. 
     */
    public boolean findProductInOrder(Order order) {    
	for (Product p : order.getProducts()) {   
    	    if (p.equals(displayedProduct)) {
    		  return true;
    	    }
    	}
    	return false;
    }
    
    public String addProductToCart() {
	Order order = orders.getCurrentOrder();
	Long counter = 1L;
	if (order.getProducts() == null) {
	    order.setProducts(new ArrayList<Product>());
	}
	if (!findProductInOrder(order)) {
	    order.addProduct(displayedProduct);
	    order.getProductAmount().put(displayedProduct.getId(), 1L);
	    if (userBean.isAuthorised()) {
		Storage.merge(order);
	    }
	} else {
	    counter = order.getProductAmount().get(displayedProduct.getId());
	    if (counter != null) {
		order.getProductAmount().put(displayedProduct.getId(), ++counter);
		if (userBean.isAuthorised()) {
		    Storage.merge(order);
		}
	    }
	}
	return "cart";
    }

   
    public Comment getComment() {
	return comment;
    }

    public void setUserBean(SessionBean userBean) {
	this.userBean = userBean;
    }

    public void setOrders(OrdersBean orders) {
	this.orders = orders;
    }

    public void sendComment() {
	if (userBean.isAuthorised()) {
	    comment.setSender(userBean.getUser());
	    comment.setProduct(displayedProduct);
	    Storage.persist(comment);
	    this.comment = new Comment();
	}
    }

    public void deleteComment() {
	// TODO check user rights
	FacesContext context = FacesContext.getCurrentInstance();
	Long id = Long.parseLong((String) context.getExternalContext()
		.getRequestParameterMap().get("comment_id"));
	Storage.removeComment(id);
    }

}
