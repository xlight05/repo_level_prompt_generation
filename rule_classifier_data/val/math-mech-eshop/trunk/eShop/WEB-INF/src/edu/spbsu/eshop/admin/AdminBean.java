package edu.spbsu.eshop.admin;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.spbsu.eshop.admin.pages.AdminRoomPage;
import edu.spbsu.eshop.admin.pages.CategoriesPage;
import edu.spbsu.eshop.admin.pages.EditGroupPage;
import edu.spbsu.eshop.admin.pages.EditProductPage;
import edu.spbsu.eshop.admin.pages.EditUserPage;
import edu.spbsu.eshop.admin.pages.GroupsPage;
import edu.spbsu.eshop.admin.pages.OrderProcessingPage;
import edu.spbsu.eshop.admin.pages.UsersPage;
import edu.spbsu.eshop.storage.Storage;
import edu.spbsu.eshop.storage.data.Customer;

public class AdminBean {

    /*
     * 14.07.09 
     * Vladimir Vasilev:
     * May be we should use some configurable xml or smth like this to avoid
     * page names hardcoding?
     */
    private final CategoriesPage catsPage = new CategoriesPage();
    private final GroupsPage groupsPage = new GroupsPage();
    private final EditGroupPage editGroupPage = new EditGroupPage();
    private final EditUserPage editUserPage = new EditUserPage();
    private final UsersPage usersPage = new UsersPage();
    private final EditProductPage editProductPage = new EditProductPage();
    private final OrderProcessingPage orderProcessingPage = new OrderProcessingPage();

    final Map<String, AdminRoomPage> pages = new Hashtable<String, AdminRoomPage>();

    private String pageRequest;

    public AdminBean() {

	List<AdminRoomPage> _pages = new LinkedList<AdminRoomPage>();

	_pages.add(usersPage);
	_pages.add(editUserPage);
	_pages.add(editGroupPage);
	_pages.add(groupsPage);
	_pages.add(catsPage);
	_pages.add(editProductPage.getAddProductItem());
	_pages.add(editProductPage.getEditProductItem());
	//_pages.add(orderProcessingPage);

	for (AdminRoomPage item : _pages) {
	    pages.put(item.getRequest(), item);
	}
    }

    public String getPageRequest() {
	return pageRequest;
    }

    public UsersPage getUsersPage() {
	return usersPage;
    }

    public CategoriesPage getCatsPage() {
	return catsPage;
    }

    public GroupsPage getGroupsPage() {
	return groupsPage;
    }

    public EditGroupPage getEditGroupPage() {
	return editGroupPage;
    }

    public EditUserPage getEditUserPage() {
	return editUserPage;
    }

    public List<Customer> getFoundUsers() {
	return Storage.getCustomers();
    }

    public void setPageRequest(String pageRequest) {
	this.pageRequest = pageRequest;
    }

    public EditProductPage getEditProductPage() {
	return editProductPage;
    }

    public OrderProcessingPage getOrderProcessingPage() {
	return orderProcessingPage;
    }

}
