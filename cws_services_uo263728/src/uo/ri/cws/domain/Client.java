package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Client extends BaseEntity {

    private String dni;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Address address;

    private Set<Vehicle> vehicles = new HashSet<>();
    private Set<PaymentMean> paymentMeans = new HashSet<>();

    public Client() {
    }

    public Client(String dni) {
	this(dni, "no-name", "no-surname", "no-email", "no-phone",
		new Address());
    }

    public Client(String dni, String name, String surname) {
	this(dni, name, surname, "no-email", "no-phone", new Address());
    }

    public Client(String dni, String name, String surname, String email,
	    String phone, Address address) {
	ArgumentChecks.isNotBlank(dni);
	ArgumentChecks.isNotBlank(name);
	ArgumentChecks.isNotBlank(surname);
	ArgumentChecks.isNotBlank(email);
	ArgumentChecks.isNotBlank(phone);
	ArgumentChecks.isNotNull(address);

	this.dni = dni;
	this.name = name;
	this.surname = surname;
	this.email = email;
	this.phone = phone;
	this.address = address;
    }

    public String getDni() {
	return dni;
    }

    public String getName() {
	return name;
    }

    public String getSurname() {
	return surname;
    }

    public String getEmail() {
	return email;
    }

    public String getPhone() {
	return phone;
    }

    public Address getAddress() {
	return address;
    }

    public void setAddress(Address address) {
	ArgumentChecks.isNotNull(address);
	this.address = address;
    }

    public Set<Vehicle> getVehicles() {
	return new HashSet<>(vehicles);
    }

    Set<Vehicle> _getVehicles() {
	return vehicles;
    }

    public Set<PaymentMean> getPaymentMeans() {
	return new HashSet<PaymentMean>(paymentMeans);
    }

    Set<PaymentMean> _getPaymentMean() {
	return paymentMeans;
    }

    @Override
    public String toString() {
	return "Client [dni=" + dni + ", name=" + name + ", surname=" + surname
		+ ", email=" + email + ", phone=" + phone + ", address="
		+ address + "]";
    }

}
