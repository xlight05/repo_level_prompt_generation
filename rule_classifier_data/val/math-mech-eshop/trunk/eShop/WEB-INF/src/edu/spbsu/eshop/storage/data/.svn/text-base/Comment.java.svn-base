package edu.spbsu.eshop.storage.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Comment extends EShopPersistanceUnit {

    @Transient
    private final int MESSAGE_MAX_LENGTH = 500;

    @Column(length = MESSAGE_MAX_LENGTH)
    private String text;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Customer sender;

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public Product getProduct() {
	return product;
    }

    public void setProduct(Product product) {
	this.product = product;
    }

    public Customer getSender() {
	return sender;
    }

    public void setSender(Customer sender) {
	this.sender = sender;
    }
}
