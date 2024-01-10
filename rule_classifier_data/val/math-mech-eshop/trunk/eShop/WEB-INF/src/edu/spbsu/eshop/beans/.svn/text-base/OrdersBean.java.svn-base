package edu.spbsu.eshop.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import edu.spbsu.eshop.dao.OrderDAO;
import edu.spbsu.eshop.dao.impl.OrderDAOImpl;
import edu.spbsu.eshop.exception.OrderValidationException;
import edu.spbsu.eshop.storage.Storage;
import edu.spbsu.eshop.storage.data.Customer;
import edu.spbsu.eshop.storage.data.Order;
import edu.spbsu.eshop.storage.data.Product;

public class OrdersBean {

    private OrderDAO orderDAO = new OrderDAOImpl();

    private SessionBean userBean;

    private RegistrationBean register;

    private long lastConfirmedOrderId;

    public OrderDAO getOrderDAO() {
	return orderDAO;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
	this.orderDAO = orderDAO;
    }

    public void setRegister(RegistrationBean register) {
	this.register = register;
    }

    public Order getCurrentOrder() {
	if (userBean.isAuthorised())
	    return Storage.getCurrentOrder(userBean.getUser());
	return userBean.getUnauthrorizedUserOrder();

    }

    public List<Product> getCurrentOrderProducts() {
	List<Product> products = getCurrentOrder().getProducts();
	if (products != null) {
	    Collections.sort(products, new ProductComparator());
	}
	return (products != null) ? products : new ArrayList<Product>();
    }

    public void setUserBean(SessionBean userBean) {
	this.userBean = userBean;
    }

    public void userAuthorized() {
	// TODO merge lists of added product and call when user authorize
    }

    public List<Order> getCustomerOrders() {
	return Storage.getOrders(userBean.getUser());
    }

    public List<Long> getProductsAmount() {
	if (getCurrentOrder().getProductAmount().values() != null) {
	    return new ArrayList<Long>(getCurrentOrder().getProductAmount()
		    .values());
	} else {
	    return new ArrayList<Long>();
	}
    }

    public String confirmOrder() {
	Order order = getCurrentOrder();
	try {
	    order.validate();
	} catch (OrderValidationException e) {
	    addErrorMessage(e.getLocalizedMessage());
	    return null;
	}
	if (userBean.isAuthorised()) {
	    // FacesContext.getCurrentInstance().getExternalContext()
	    // .getSessionMap().put("orderId", order.getId());
	    List<Product> products = order.getProducts();
	    for (Product p: products) {
		Long counter  = order.getProductAmount().get(p.getId());
		p.setAmount(p.getAmount().intValue() - counter.intValue());
		Storage.merge(p);
	    }
	    order.setState(Order.State.Confirmed);
	    Storage.merge(order);
	    return "orderConfirmation";
	} else {
	    return "enterUserData";
	}
    }

    public String unregisteredSaveOrder() {
	Customer customer = register.getRegister();
	Order order = getCurrentOrder();
	order.setCustomer(customer);
	try {
	    order.validate();
	} catch (OrderValidationException e) {
	    addErrorMessage(e.getLocalizedMessage());
	    return "";
	}
	// FacesContext.getCurrentInstance().getExternalContext()
	// .getSessionMap().put("orderId", order.getId());
	List<Product> products = order.getProducts();
	for (Product p : products) {
	    Long counter = order.getProductAmount().get(p.getId());
	    p.setAmount(p.getAmount().intValue() - counter.intValue());
	    Storage.merge(p);
	}
	order.setState(Order.State.Confirmed);
	Storage.persist(customer, order);
	lastConfirmedOrderId = order.getId();
	userBean.resetUnauthrorizedUserOrder();
	return "orderConfirmation";
    }

    public boolean getIsConfirmed() {
	return getLastConfirmed() != null
		&& getLastConfirmed().getState() != null
		&& getLastConfirmed().getState().equals(Order.State.Confirmed);
    }

    public Order getLastConfirmed() {
	if (userBean.isAuthorised()) {
	    return orderDAO.getLastConfirmed(userBean.getUser());
	} else if (lastConfirmedOrderId >= 0) {
	    try {
		Order order = (Order) Storage.getOrder(lastConfirmedOrderId);
		return order;
	    } catch (NoResultException e) {
		return null;
	    }
	} else {
	    return null;
	}
    }

    private void addErrorMessage(String message) {
	FacesContext.getCurrentInstance().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    class ProductComparator implements Comparator<Product> {

	@Override
	public int compare(Product p1, Product p2) {
	    return p1.getId().compareTo(p2.getId());
	}

    }
}
