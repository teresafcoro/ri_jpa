package uo.ri.cws.domain;

import java.time.LocalDate;

import uo.ri.util.assertion.ArgumentChecks;

public class CreditCard extends PaymentMean {

    private String number;
    private String type;
    private LocalDate validThru;

    public CreditCard() {
    }

    public CreditCard(String number) {
	this(number, "UNKNOWN", LocalDate.now().plusDays(1L));
    }

    public CreditCard(String number, String type, LocalDate validThru) {
	ArgumentChecks.isNotBlank(number);
	ArgumentChecks.isNotBlank(type);
	ArgumentChecks.isNotNull(validThru);

	this.number = number;
	this.type = type;
	this.validThru = validThru;
    }

    public String getNumber() {
	return number;
    }

    public String getType() {
	return type;
    }

    public LocalDate getValidThru() {
	return validThru;
    }

    public void setValidThru(LocalDate date) {
	if (this.validThru.isAfter(date) & super.getAccumulated() != 0)
	    throw new IllegalStateException(
		    "Cannot change date to previous one if credit card has been used to pay");
	this.validThru = date;
    }

    public boolean isValidNow() {
	return this.validThru.isAfter(LocalDate.now());
    }

    @Override
    public void pay(double importe) {
	if (!isValidNow())
	    throw new IllegalStateException(
		    "Cannot pay if credit card has been expired");
	super.pay(importe);
    }

    @Override
    public String toString() {
	return "CreditCard [number=" + number + ", type=" + type
		+ ", validThru=" + validThru + "]";
    }

}
