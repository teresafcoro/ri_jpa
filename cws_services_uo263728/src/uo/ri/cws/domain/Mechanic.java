package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Mechanic extends BaseEntity {

    // natural attributes
    private String dni;
    private String name;
    private String surname;

    // accidental attributes
    private Set<WorkOrder> assigned = new HashSet<>();
    private Set<Intervention> interventions = new HashSet<>();
    private Contract contract;
    private Set<Contract> terminatedcontracts = new HashSet<>();

    public Mechanic() {
    }

    public Mechanic(String dni) {
	this(dni, "no-name", "no-surname");
    }

    public Mechanic(String dni, String name, String surname) {
	ArgumentChecks.isNotBlank(dni);
	ArgumentChecks.isNotBlank(name);
	ArgumentChecks.isNotBlank(surname);

	this.dni = dni;
	this.name = name;
	this.surname = surname;
    }

    public String getDni() {
	return dni;
    }

    public String getSurname() {
	return surname;
    }

    public void setSurname(String surname) {
	ArgumentChecks.isNotBlank(surname);
	this.surname = surname;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	ArgumentChecks.isNotBlank(name);
	this.name = name;
    }

    public Set<WorkOrder> getAssigned() {
	return new HashSet<>(assigned);
    }

    Set<WorkOrder> _getAssigned() {
	return assigned;
    }

    public Set<Intervention> getInterventions() {
	return new HashSet<>(interventions);
    }

    Set<Intervention> _getInterventions() {
	return interventions;
    }

    public Optional<Contract> getContractInForce() {
	return Optional.ofNullable(contract);
    }

    void _setContractInForce(Contract contract) {
	this.contract = contract;
    }

    public Set<Contract> getTerminatedContracts() {
	return new HashSet<>(terminatedcontracts);
    }

    public Set<Contract> _getTerminatedContracts() {
	return terminatedcontracts;
    }

    public boolean isInForce() {
	return this.contract.getState().equals(ContractState.IN_FORCE);
    }

    public double getAmount() {
	double amount = 0.0;
	for (WorkOrder workOrder : assigned)
	    amount += workOrder.getAmount();
	return amount;
    }

    @Override
    public String toString() {
	return "Mechanic [dni=" + dni + ", surname=" + surname + ", name="
		+ name + "]";
    }

}
