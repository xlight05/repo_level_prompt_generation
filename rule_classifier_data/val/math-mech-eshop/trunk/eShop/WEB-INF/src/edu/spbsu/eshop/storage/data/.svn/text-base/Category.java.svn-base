package edu.spbsu.eshop.storage.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sun.istack.internal.Nullable;

@Entity
@Table(name = "categories")
public class Category extends EShopPersistanceUnit {

    @Nullable
    @ManyToOne
    private Category parent = null;

    @Column(name = "name")
    private String name = "";

    public Category getParent() {
	return parent;
    }

    public void setParent(Category parentCategory) {
	this.parent = parentCategory;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

}
