package uo.ri.cws.domain;

import uo.ri.cws.domain.Invoice.InvoiceState;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Charge extends BaseEntity {

    // natural attributes
    private double amount = 0.0;

    // accidental attributes
    private Invoice invoice;
    private PaymentMean paymentMean;

    public Charge() {
    }

    public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
	ArgumentChecks.isNotNull(invoice);
	ArgumentChecks.isNotNull(paymentMean);
	ArgumentChecks.isTrue(amount >= 0.0);

	this.amount = amount;
	paymentMean.pay(amount);
	Associations.ToCharge.link(paymentMean, this, invoice);
    }

    /**
     * Unlinks this charge and restores the accumulated to the payment mean
     * 
     * @throws IllegalStateException if the invoice is already settled
     */
    public void rewind() {
	if (invoice.getState() == InvoiceState.PAID)
	    throw new IllegalStateException("Invoice already settled");
	paymentMean.pay(-amount);
	Associations.ToCharge.unlink(this);
    }

    public double getAmount() {
	return amount;
    }

    public Invoice getInvoice() {
	return invoice;
    }

    public PaymentMean getPaymentMean() {
	return paymentMean;
    }

    public void setAmount(double amount) {
	ArgumentChecks.isTrue(amount >= 0.0);
	this.amount = amount;
    }

    public void _setPaymentMean(PaymentMean pm) {
	this.paymentMean = pm;
    }

    public void _setInvoice(Invoice invoice) {
	this.invoice = invoice;
    }

    @Override
    public String toString() {
	return "Charge [amount=" + amount + ", invoice=" + invoice
		+ ", paymentMean=" + paymentMean + "]";
    }

}
