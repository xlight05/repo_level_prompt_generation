package edu.spbsu.eshop.beans;

import edu.spbsu.eshop.storage.Storage;
import edu.spbsu.eshop.storage.data.Customer;
import edu.spbsu.eshop.storage.data.Order;

/**
 * Class represents customer of eShop
 * 
 * @author Ilya Shurigyn
 */
public class SessionBean {

    private boolean authorised = false;
    private String text = "";
    private Order unauthrorizedUserOrder = new Order();
    private Customer unauthrorizedUser;
    private Long userId;
    private String searchRequest;

    public SessionBean() {
	AuthBean auth = new AuthBean();
	auth.setSessionBean(this);
	this.authorised = auth.authoriseByCookie();
	if (!authorised) {
	    unauthrorizedUser = AuthBean.createUnauthorizedUser();
	}
    }

    public Order getUnauthrorizedUserOrder() {
	return unauthrorizedUserOrder;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    /**
     * this method sync user with DB on each request.
     * 
     * @return current user
     */
    public Customer getUser() {
	if (isAuthorised())
	    return Storage.getCustomer(userId);
	return unauthrorizedUser;
    }

    public boolean isAuthorised() {
	return authorised;
    }

    void setAuthorised(boolean authorised) {
	this.authorised = authorised;
    }

    public void logout() {
	this.setAuthorised(false);
    }

    void setUser(Customer c) {
	this.userId = c.getId();
    }

    public String getSearchRequest() {
	return searchRequest;
    }

    public void setSearchRequest(String searchRequest) {
	this.searchRequest = searchRequest;
    }

    public void resetUnauthrorizedUserOrder() {
	this.unauthrorizedUserOrder = new Order();

    }

}
