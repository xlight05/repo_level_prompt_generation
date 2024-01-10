package edu.spbsu.eshop.admin.pages;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import edu.spbsu.eshop.storage.Storage;
import edu.spbsu.eshop.storage.data.Order;
import edu.spbsu.eshop.storage.data.Product;

public class OrderProcessingPage extends AbstractAdminRoomPage {
    private Order order;

    public OrderProcessingPage() {
	super("order_processing", "order Processing", "orderProcessing.jsf");
    }

    public List<Order> getOrders() {
	return Storage.getOrders();
    }

    public String save() {
	// OrderCreationTest test = new OrderCreationTest();
	// test.testOrderCreation();
	return "content";
    }

    public Order getOrder() {
	return order;
    }

    public void setOrder(Order order) {
	this.order = order;
    }

    public void showProducts() {
	FacesContext context = FacesContext.getCurrentInstance();
	Long id = Long.parseLong((String) context.getExternalContext()
		.getRequestParameterMap().get("id"));
	order = (Order) Storage.getOrder(id);
    }

    public List<Product> getFoundProducts() {
	if (order != null) {
	    if (order.getProducts() != null) {
		List<Product> list = new ArrayList<Product>(order.getProducts());
		return list;
	    }
	}
	return null;
    }
}
