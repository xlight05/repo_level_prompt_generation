package edu.spbsu.eshop.storage.data;

import javax.persistence.*;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

@Entity
@Table(name = "customers")
@UniqueConstraint(columnNames = { "login", "email" })
public class Customer extends EShopPersistanceUnit {

    @Column(name = "login")
    private String login;

    @Column(name = "password_hash")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @NotNull
    @JoinColumn(name = "address_id")
    private Address address = new Address();

    @ManyToOne
    @Nullable
    @JoinColumn
    private Group group = null;

    public Customer() {
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getSurname() {
	return surname;
    }

    public void setSurname(String surname) {
	this.surname = surname;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public Address getAddress() {
	return address;
    }

    public void setAddress(Address address) {
	this.address = address;
    }

    public Group getGroup() {
	return group;
    }

    public void setGroup(Group group) {
	this.group = group;
    }

    @Override
    public String toString() {
	return login + ":" + password;

    }

}
