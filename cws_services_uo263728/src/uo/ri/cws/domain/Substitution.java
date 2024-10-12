package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Substitution extends BaseEntity {

    // natural attributes
    private int quantity;

    // accidental attributes
    private SparePart sparePart;
    private Intervention intervention;

    public Substitution() {
    }

    public Substitution(SparePart sparePart, Intervention intervention,
	    int quantity) {
	ArgumentChecks.isNotNull(sparePart);
	ArgumentChecks.isNotNull(intervention);
	ArgumentChecks.isTrue(quantity > 0);

	this.quantity = quantity;
	Associations.Substitute.link(sparePart, this, intervention);
    }

    public int getQuantity() {
	return quantity;
    }

    public SparePart getSparePart() {
	return sparePart;
    }

    void _setSparePart(SparePart sparePart) {
	this.sparePart = sparePart;
    }

    public Intervention getIntervention() {
	return intervention;
    }

    void _setIntervention(Intervention intervention) {
	this.intervention = intervention;
    }

    public double getAmount() {
	return quantity * sparePart.getPrice();
    }

    @Override
    public String toString() {
	return "Substitution [quantity=" + quantity + ", sparePart=" + sparePart
		+ ", intervention=" + intervention + "]";
    }

}
