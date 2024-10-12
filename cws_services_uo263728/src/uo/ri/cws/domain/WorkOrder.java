package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class WorkOrder extends BaseEntity {

    public enum WorkOrderState {
	OPEN, ASSIGNED, FINISHED, INVOICED
    }

    // natural attributes
    private LocalDateTime date;
    private String description;
    private double amount = 0.0;
    private WorkOrderState state = WorkOrderState.OPEN;

    // accidental attributes
    private Vehicle vehicle;
    private Mechanic mechanic;
    private Invoice invoice;
    private Set<Intervention> interventions = new HashSet<>();

    public WorkOrder() {
    }

    public WorkOrder(Vehicle vehicle) {
	this(vehicle, LocalDateTime.now(), "no-description");
    }

    public WorkOrder(Vehicle vehicle, String description) {
	this(vehicle, LocalDateTime.now(), description);
    }

    public WorkOrder(Vehicle vehicle, LocalDateTime atStartOfDay) {
	this(vehicle, atStartOfDay, "no-description");
    }

    public WorkOrder(Vehicle vehicle, LocalDateTime now, String description) {
	ArgumentChecks.isNotNull(now);
	ArgumentChecks.isNotBlank(description);
	ArgumentChecks.isNotNull(vehicle);

	this.date = now.truncatedTo(ChronoUnit.MILLIS);
	this.description = description;
	Associations.Fix.link(vehicle, this);
    }

    /**
     * Changes it to INVOICED state given the right conditions This method is
     * called from Invoice.addWorkOrder(...)
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not FINISHED, or -
     *                               The work order is not linked with the
     *                               invoice
     */
    public void markAsInvoiced() {
	if (!isFinished())
	    throw new IllegalStateException("The work order is not FINISHED");
	if (this.invoice == null)
	    throw new IllegalStateException(
		    "The work order is not linked with the invoice");
	this.state = WorkOrderState.INVOICED;
    }

    /**
     * Changes it to FINISHED state given the right conditions and computes the
     * amount; Also unlinked from the previous mechanic.
     *
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in ASSIGNED
     *                               state, or - The work order is not linked
     *                               with a mechanic
     */
    public void markAsFinished() {
	if (this.state != WorkOrderState.ASSIGNED)
	    throw new IllegalStateException("The work order is not ASSIGNED");
	if (this.mechanic == null)
	    throw new IllegalStateException(
		    "The work order is not linked with a mechanic");
	this.state = WorkOrderState.FINISHED;
	this.amount = 0.0;
	for (Intervention intervention : interventions)
	    amount += intervention.getAmount();
	Associations.Assign.unlink(mechanic, this);
    }

    /**
     * Changes it back to FINISHED state given the right conditions This method
     * is called from Invoice.removeWorkOrder(...)
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not INVOICED, or -
     *                               The work order is still linked with the
     *                               invoice
     */
    public void markBackToFinished() {
	if (!isInvoiced())
	    throw new IllegalStateException("The work order is not INVOICED");
	if (this.invoice != null)
	    throw new IllegalStateException(
		    "The work order is still linked with the invoice");
	this.state = WorkOrderState.FINISHED;
    }

    /**
     * Links (assigns) the work order to a mechanic and then changes its state
     * to ASSIGNED
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in OPEN state,
     *                               or - The work order is already linked with
     *                               another mechanic
     */
    public void assignTo(Mechanic mechanic) {
	if (this.state != WorkOrderState.OPEN)
	    throw new IllegalStateException(
		    "The work order is not in OPEN state");
	if (this.mechanic != null)
	    throw new IllegalStateException(
		    "The work order is already linked with another mechanic");
	Associations.Assign.link(mechanic, this);
	this.state = WorkOrderState.ASSIGNED;
    }

    /**
     * Unlinks (deassigns) the work order and the mechanic and then changes its
     * state back to OPEN
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in ASSIGNED
     *                               state
     */
    public void desassign() {
	if (this.state != WorkOrderState.ASSIGNED)
	    throw new IllegalStateException(
		    "The work order is not in ASSIGNED state");
	Associations.Assign.unlink(mechanic, this);
	this.state = WorkOrderState.OPEN;
    }

    /**
     * In order to assign a work order to another mechanic is first have to be
     * moved back to OPEN state.
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in FINISHED
     *                               state
     */
    public void reopen() {
	if (this.state != WorkOrderState.FINISHED)
	    throw new IllegalStateException(
		    "The work order is not in FINISHED state");
	this.state = WorkOrderState.OPEN;
    }

    public double getAmount() {
	return amount;
    }

    public WorkOrderState getState() {
	return state;
    }

    public WorkOrderState getStatus() {
	return state;
    }

    public LocalDateTime getDate() {
	return date;
    }

    public String getDescription() {
	return description;
    }

    public Set<Intervention> getInterventions() {
	return new HashSet<>(interventions);
    }

    Set<Intervention> _getInterventions() {
	return interventions;
    }

    public Vehicle getVehicle() {
	return vehicle;
    }

    void _setVehicle(Vehicle vehicle) {
	this.vehicle = vehicle;
    }

    public Mechanic getMechanic() {
	return mechanic;
    }

    void _setMechanic(Mechanic mechanic) {
	this.mechanic = mechanic;
    }

    public Invoice getInvoice() {
	return invoice;
    }

    void _setInvoice(Invoice invoice) {
	this.invoice = invoice;
    }

    public boolean isInvoiced() {
	return this.state == WorkOrderState.INVOICED;
    }

    public boolean isFinished() {
	return this.state == WorkOrderState.FINISHED;
    }

    public void setStatusForTesting(WorkOrderState invoiced) {
	this.state = invoiced;
    }

    public void setAmountForTesting(double money) {
	ArgumentChecks.isTrue(money >= 0.0);
	this.amount = money;
    }

    @Override
    public String toString() {
	return "WorkOrder [date=" + date + ", description=" + description
		+ ", amount=" + amount + ", state=" + state + ", vehicle="
		+ vehicle + "]";
    }

}
