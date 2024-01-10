package edu.spbsu.eshop.admin.pages;

import edu.spbsu.eshop.storage.Storage;
import edu.spbsu.eshop.storage.data.Customer;
import edu.spbsu.eshop.storage.data.Group;

public class EditUserPage extends AbstractAdminRoomPage {
    private Customer customer;

    public EditUserPage() {
	super("edit_user", null, "editUser.jsf");
	this.setAction(new AbstractOnPageRequestAction() {

	    @Override
	    protected Object getObjectById(Long id) {
		return Storage.getCustomer(id);
	    }

	    @Override
	    protected void setObject(Object object) {
		customer = (Customer) object;
	    }

	});
	this.setNecessaryPermsissions(Group.Permission.EditUser);

    }

    public Group[] getGroups() {
	return Storage.getGroups();
    }

    public String save() {
	Storage.merge(customer);
	return "content";
    }

    public Customer getCustomer() {
	return customer;
    }

    public Long getGroupId() {
	if (customer.getGroup() == null)
	    return null;
	return customer.getGroup().getId();
    }

    public void setGroupId(Long id) {
	customer.setGroup(Storage.getGroup(id));
    }

}
