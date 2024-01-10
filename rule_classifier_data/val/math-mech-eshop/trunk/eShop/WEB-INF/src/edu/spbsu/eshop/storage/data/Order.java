package edu.spbsu.eshop.storage.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import edu.spbsu.eshop.exception.OrderValidationException;

@Entity
@Table(name = "orders")
public class Order extends EShopPersistanceUnit {

    @OneToMany(fetch = FetchType.EAGER)
    private List<Product> products;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @org.hibernate.annotations.CollectionOfElements(fetch = FetchType.EAGER)
    @JoinTable(name = "ORDERS_PRODUCTS_AMOUNT", joinColumns = @JoinColumn(name = "id"))
    @org.hibernate.annotations.MapKey(columns = @Column(name = "PRODUCT_ID"))
    @Column(name = "PRODUCT_AMOUNT")
    private Map<Long, Long> productAmount = new HashMap<Long, Long>();

    public Map<Long, Long> getProductAmount() {
	return productAmount;
    }

    public void setProductAmount(Map<Long, Long> productAmount) {
	this.productAmount = productAmount;
    }

    public List<Product> getProducts() {
	return products;
    }

    public void setProducts(List<Product> products) {
	this.products = products;
    }

    public State getState() {
	return state;
    }

    public void setState(State state) {
	this.state = state;
    }

    public Customer getCustomer() {
	return customer;
    }

    public void setCustomer(Customer customer) {
	this.customer = customer;
    }

    public void addProduct(Product product) {
	if (products == null) {
	    products = new ArrayList<Product>();
	}
	products.add(product);
    }

    @Transient
    public int getCount() {
	int sum = 0;
	if (products != null) {
	    for (Product product : products) {
		if (productAmount.get(product.getId()) != null) {
		    sum += product.getPrice()
			    * productAmount.get(product.getId());
		} else {
		    sum += product.getPrice();
		}
	    }
	}
	return sum;
    }

    @Transient
    public void validate() throws OrderValidationException {
	if (products == null) {
	    throw new OrderValidationException(
		    "Empty order can not be confirmed!");
	} else if (products.size() == 0) {
	    throw new OrderValidationException(
		    "Empty order can not be confirmed!");
	}
    }

    public static enum State {
	Current, Paid, Sended, Finished, Confirmed
    }
}
