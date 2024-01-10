package edu.spbsu.eshop.admin.pages;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import edu.spbsu.eshop.storage.Storage;
import edu.spbsu.eshop.storage.data.Category;
import edu.spbsu.eshop.storage.data.EShopPersistanceUnit;
import edu.spbsu.eshop.storage.data.Group;

public class CategoriesPage extends AbstractAdminRoomPage {
    private Category currentCat;
    private String newCatName = "";

    public CategoriesPage() {
	super("cats", "list categories", "listCategories.jsf");
	this.setNecessaryPermsissions(Group.Permission.AddCategories);
	this.setAction(new AbstractOnPageRequestAction() {

	    @Override
	    protected Object getObjectById(Long id) {
		try {
		    return Storage.getCategory(id);
		} catch (NoResultException e) {
		    // TODO throw no such category
		    return null;
		}
	    }

	    @Override
	    protected void idIsNotLong(String idValue) {
		currentCat = null;

	    }

	    @Override
	    protected void setObject(Object object) {
		currentCat = (Category) object;
	    }

	});
    }

    public String getCategoryListView() {

	if (currentCat == null)
	    return "";

	String result = currentCat.getName();
	Category cat = currentCat.getParent();
	while (cat != null) {
	    result = cat.getName() + "->" + result;
	    cat = cat.getParent();
	}

	return result;
    }

    public void addCategory() {
	Category cat = new Category();
	cat.setName(newCatName);
	cat.setParent(currentCat);
	newCatName = "";

	Storage.persist(cat);
    }

    public String getNewCatName() {
	return newCatName;
    }

    public void setNewCatName(String newCatName) {
	this.newCatName = newCatName;
    }

    public EShopPersistanceUnit[] getChildCategories() {
	return Storage.getChildCategories(currentCat);
    }

    public List<Category> getParentCategories() {
	List<Category> list = new ArrayList<Category>();
	if (currentCat != null) {
	    Category cat = currentCat.getParent();
	    while (cat != null) {
		list.add(0, cat);
		cat = cat.getParent();
	    }
	}
	return list;
    }

    public void setCategory(Category category) {
	this.currentCat = category;
    }

    public Category getCurrentCat() {
	return currentCat;
    }

    public String removeCategory() {
	FacesContext context = FacesContext.getCurrentInstance();
	long id = Long.parseLong((String) context.getExternalContext()
		.getRequestParameterMap().get("cid"));
	// ErrorBean.setErrorMessage(id+"");
	EShopPersistanceUnit cat = Storage.getCategory(id);
	Storage.remove(cat);
	return "";
    }

}
