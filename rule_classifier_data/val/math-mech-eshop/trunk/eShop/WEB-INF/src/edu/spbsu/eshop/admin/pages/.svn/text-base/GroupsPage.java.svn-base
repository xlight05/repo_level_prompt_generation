package edu.spbsu.eshop.admin.pages;

import javax.faces.context.FacesContext;

import edu.spbsu.eshop.storage.Storage;
import edu.spbsu.eshop.storage.data.Group;

public class GroupsPage extends AbstractAdminRoomPage {

    public GroupsPage() {
	super("groups", "groups", "groups.jsf");
	this.setNecessaryPermsissions(Group.Permission.ListGroup);
    }

    public Group[] getGroups() {
	return Storage.getGroups();
    }

    public void addGroup() {
	Group group = new Group();
	group.setName("new");
	// TODO add especial persist method for group
	Storage.persist(group);
    }

    /**
     * remove group is very unsafe. be sure that there is no users in this
     * group.
     */

    public void removeGroup() {
	FacesContext context = FacesContext.getCurrentInstance();
	long id = Long.parseLong((String) context.getExternalContext()
		.getRequestParameterMap().get("id"));
	Group group = Storage.getGroup(id);
	Storage.remove(group);
    }
}
