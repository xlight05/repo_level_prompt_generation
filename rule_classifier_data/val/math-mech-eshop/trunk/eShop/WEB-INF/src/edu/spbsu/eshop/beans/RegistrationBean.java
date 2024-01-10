package edu.spbsu.eshop.beans;

import edu.spbsu.eshop.storage.data.Customer;
import edu.spbsu.eshop.storage.data.Group;
import edu.spbsu.eshop.storage.Storage;

public class RegistrationBean {
    private Customer register;

    public RegistrationBean() {
	register = new Customer();
    }

    public Customer getRegister() {

	return register;
    }

    public void setRegister(Customer register) {
	this.register = register;
    }

    public String doRegistration() {
	Group group = Storage.getGroupForRegistred();
	register.setGroup(group);
	Storage.persistUser(register);
	register = new Customer();
	return "redirect";
    }
}