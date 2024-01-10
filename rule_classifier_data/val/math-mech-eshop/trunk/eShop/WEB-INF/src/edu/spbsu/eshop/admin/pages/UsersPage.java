package edu.spbsu.eshop.admin.pages;

import javax.faces.context.FacesContext;

import edu.spbsu.eshop.storage.Storage;
import edu.spbsu.eshop.storage.data.Customer;
import edu.spbsu.eshop.storage.data.Group;

public class UsersPage extends AbstractAdminRoomPage {

    public UsersPage() {
	super("users", "list users", "manageUsers.jsf");
	this.setNecessaryPermsissions(Group.Permission.ListUsers);
    }

    public void removeUser() {
	FacesContext context = FacesContext.getCurrentInstance();
	Long id = Long.parseLong((String) context.getExternalContext()
		.getRequestParameterMap().get("id"));
	Customer customer = Storage.getCustomer(id);
	Storage.remove(customer);
    }

    public void addUser() {
	Storage.persistUser(new Customer());
    }

}
