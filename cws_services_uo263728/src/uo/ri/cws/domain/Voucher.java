package uo.ri.cws.domain;

import uo.ri.util.assertion.ArgumentChecks;

public class Voucher extends PaymentMean {

    private String code;
    private double available = 0.0;
    private String description;

    public Voucher() {
    }

    public Voucher(String code, String description, double available) {
	ArgumentChecks.isNotBlank(code);
	ArgumentChecks.isTrue(available >= 0);
	ArgumentChecks.isNotBlank(description);

	this.code = code;
	this.available = available;
	this.description = description;
    }

    public Voucher(String code, double available) {
	this(code, "no-description", available);
    }

    public String getCode() {
	return code;
    }

    public double getAvailable() {
	return available;
    }

    public String getDescription() {
	return description;
    }

    /**
     * Augments the accumulated (super.pay(amount)) and decrements the available
     * 
     * @throws IllegalStateException if not enough available to pay
     */
    @Override
    public void pay(double amount) {
	if (amount > available)
	    throw new IllegalStateException("No enough money available to pay");

	super.pay(amount);
	this.available -= amount;
    }

    @Override
    public String toString() {
	return "Voucher [code=" + code + ", available=" + available
		+ ", description=" + description + "]";
    }

}
