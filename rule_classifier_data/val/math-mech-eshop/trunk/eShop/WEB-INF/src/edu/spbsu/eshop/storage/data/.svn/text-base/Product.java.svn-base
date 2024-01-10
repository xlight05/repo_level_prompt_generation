package edu.spbsu.eshop.storage.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product extends EShopPersistanceUnit {

    public static final int MAX_DESCRIPTION_LENGTH = 500;

    @ManyToOne
    private Category category = null;

    @Column(name = "photo_file")
    private String photoFileName = "";

    @Column(name = "description", length = MAX_DESCRIPTION_LENGTH)
    private String description = "";

    @Column(name = "price")
    private Integer price = 0;

    @Column(name = "name")
    private String name = "";


    @Column(name = "amount")
    private Integer amount = 1;


    public Integer getAmount() {
	return amount;
    }

    public void setAmount(Integer amount) {
	this.amount = amount;
    }

    public String getPhotoFileName() {
	return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
	this.photoFileName = photoFileName;
    }

    public Category getCategory() {
	return category;
    }

    public void setCategory(Category category) {
	this.category = category;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public Integer getPrice() {
	return price;
    }

    public void setPrice(Integer price) {
	this.price = price;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public int hashCode() {
	return super.getId().hashCode();
    }

}
