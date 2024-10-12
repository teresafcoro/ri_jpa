package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.math.Round;

public class Invoice extends BaseEntity {

    public enum InvoiceState {
	NOT_YET_PAID, PAID
    }

    // natural attributes
    private Long number;
    private LocalDate date;
    private double amount;
    private double vat;
    private InvoiceState state = InvoiceState.NOT_YET_PAID;

    // accidental attributes
    private Set<WorkOrder> workOrders = new HashSet<>();
    private Set<Charge> charges = new HashSet<>();

    public Invoice() {
    }

    public Invoice(Long number) {
	this(number, LocalDate.now(), List.of());
    }

    public Invoice(Long number, LocalDate date) {
	this(number, date, List.of());
    }

    public Invoice(Long number, List<WorkOrder> workOrders) {
	this(number, LocalDate.now(), workOrders);
    }

    public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {
	ArgumentChecks.isNotNull(number);
	ArgumentChecks.isNotNull(date);
	ArgumentChecks.isNotNull(workOrders);

	this.number = number;
	this.date = date;
	for (WorkOrder workOrder : workOrders)
	    addWorkOrder(workOrder);
    }

    /**
     * Computes amount and vat (vat depends on the date)
     */
    private void computeAmount() {
	double amount = 0.0;
	for (WorkOrder wo : workOrders)
	    amount += wo.getAmount();
	this.amount = Round.twoCents(amount * (1.0 + getVat()));
    }

    /**
     * Adds (double links) the workOrder to the invoice and updates the amount
     * and vat
     * 
     * @param workOrder
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
     */
    public void addWorkOrder(WorkOrder workOrder) {
	if (this.state != InvoiceState.NOT_YET_PAID)
	    throw new IllegalStateException(
		    "Cannot link if invoice status is paid");

	ArgumentChecks.isNotNull(workOrder);
	Associations.ToInvoice.link(this, workOrder);
	workOrder.markAsInvoiced();
	computeAmount();
    }

    /**
     * Removes a work order from the invoice and recomputes amount and vat
     * 
     * @param workOrder
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
     */
    public void removeWorkOrder(WorkOrder workOrder) {
	if (this.state != InvoiceState.NOT_YET_PAID)
	    throw new IllegalStateException(
		    "Cannot link if invoice status is paid");

	ArgumentChecks.isNotNull(workOrder);
	Associations.ToInvoice.unlink(this, workOrder);
	workOrder.markBackToFinished();
	computeAmount();
    }

    /**
     * Marks the invoice as PAID, but
     * 
     * @throws IllegalStateException if - Is already settled - Or the amounts
     *                               paid with charges to payment means do not
     *                               cover the total of the invoice
     */
    public void settle() {
	if (this.state == InvoiceState.PAID)
	    throw new IllegalStateException("Is already settled");

	double totalCharges = getTotalCharges();
	if (totalCharges < this.getAmount() - 0.01)
	    throw new IllegalStateException(
		    "The amounts paid with charges to payment means do not cover the total of the invoice");
	if (totalCharges > this.getAmount() + 0.01)
	    throw new IllegalStateException(
		    "The amounts paid with charges to payment means (its invoice) is being overpaid");

	this.state = InvoiceState.PAID;
    }

    private double getTotalCharges() {
	double total = 0.0;
	for (Charge ch : charges)
	    total += ch.getAmount();
	return total;
    }

    public Long getNumber() {
	return number;
    }

    public LocalDate getDate() {
	return date;
    }

    public double getAmount() {
	return amount;
    }

    public double getVat() {
	if (date.isBefore(LocalDate.of(2012, 7, 1)))
	    this.vat = 0.18;
	else
	    this.vat = 0.21;
	return this.vat;
    }

    public InvoiceState getState() {
	return state;
    }

    public Set<WorkOrder> getWorkOrders() {
	return new HashSet<>(workOrders);
    }

    Set<WorkOrder> _getWorkOrders() {
	return workOrders;
    }

    public Set<Charge> getCharges() {
	return new HashSet<>(charges);
    }

    Set<Charge> _getCharges() {
	return charges;
    }

    public boolean isNotSettled() {
	return this.state != InvoiceState.PAID;
    }

    @Override
    public String toString() {
	return "Invoice [number=" + number + ", date=" + date + ", amount="
		+ amount + ", vat=" + getVat() + ", state=" + state + "]";
    }

}
