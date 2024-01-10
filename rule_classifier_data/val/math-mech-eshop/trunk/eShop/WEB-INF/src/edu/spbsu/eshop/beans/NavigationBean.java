package edu.spbsu.eshop.beans;

import edu.spbsu.eshop.storage.Storage;
import edu.spbsu.eshop.storage.data.Category;

public class NavigationBean {
    // TODO cache categories?
    // be aware, that after lunching application list of cats can be changed

    private String catId;
    private String pageId = "/index.jsf";
    private Category currentCategory;
    private static final String mainPage = "http://eshop2.s156.eatj.com";

    public String getMainPage() {
	return mainPage;
    }

    public Category getCurrentCategory() {
	return currentCategory;
    }

    // public String getCurrentPageURL() {
    // return PrettyContext.getCurrentInstance().getOriginalRequestUrl();
    // }
    public Category[] getRootCategories() {
	return Storage.getChildCategories(null);
    }

    public Category getFirstLevelCategory() {

	if (currentCategory == null)
	    return null;

	Category cat = this.currentCategory;
	while (cat.getParent() != null) {
	    cat = cat.getParent();
	}
	return cat;
    }

    public void setCatId(String catId) {
	try {
	    currentCategory = Storage.getCategory(Long.valueOf(catId));
	} catch (Exception e) {
	    currentCategory = null;
	    setPageId("/error.jsf");
	}
	this.catId = catId;
    }

    public Category[] getFirstLevelsChildCategories() {
	return Storage.getChildCategories(getFirstLevelCategory());
    }

    public Category[] getCurrentCategorysChildren() {
	return Storage.getChildCategories(currentCategory);
    }

    public String getCatId() {
	return catId;
    }

    public String getPageId() {
	return pageId;
    }

    public void setPageId(String pageId) {
	this.pageId = pageId;
    }

}