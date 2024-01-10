package edu.spbsu.eshop.storage.data;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private Long id;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getStreet() {
	return street;
    }

    public void setStreet(String street) {
	this.street = street;
    }

    public String getPostal_code() {
	return postal_code;
    }

    public void setPostal_code(String postal_code) {
	this.postal_code = postal_code;
    }

    public String getBuilding() {
	return building;
    }

    public void setBuilding(String building) {
	this.building = building;
    }

    public String getApartment() {
	return apartment;
    }

    public void setApartment(String apartment) {
	this.apartment = apartment;
    }

    @Column(name = "postal_code")
    private String postal_code;

    @Column(name = "building")
    private String building;

    @Column(name = "apartament")
    private String apartment;

}
