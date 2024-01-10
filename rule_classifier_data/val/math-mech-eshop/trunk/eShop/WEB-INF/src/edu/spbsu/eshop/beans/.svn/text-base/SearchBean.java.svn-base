package edu.spbsu.eshop.beans;

import java.util.Comparator;
import java.util.List;

import edu.spbsu.eshop.storage.Storage;
import edu.spbsu.eshop.storage.data.Product;

public class SearchBean {
    private SessionBean sessionBean;

    public void setSessionBean(SessionBean sessionBean) {
	this.sessionBean = sessionBean;
    }

    private NavigationBean navigationBean;

    public List<Product> getCurrentCategorysProductsOrderedByName() {
	List<Product> result = Storage.getProductsForCategory(navigationBean
		.getCurrentCategory());
	java.util.Collections.sort(result, new ProductComparator());
	return result;
    }

    public NavigationBean getNavigationBean() {
	return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
	this.navigationBean = navigationBean;
    }

    public List<Product> getSearchResult() {
	// TODO escape request
	String request = sessionBean.getSearchRequest();
	request = request.trim();
	if (request.indexOf(' ') == -1)
	    return Storage.searchProductsByName(request);
	sessionBean.setSearchRequest("");
	return Storage.searchProductsByName(request.split(" "));
    }

}

class ProductComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
	return p1.getName().compareTo(p2.getName());
    }

}
