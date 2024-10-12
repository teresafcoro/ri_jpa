package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class ContractType extends BaseEntity {

    private String name;
    private double compensationDays;

    private Set<Contract> contracts = new HashSet<>();

    public ContractType() {
    }

    public ContractType(String name, double compensationDays) {
	ArgumentChecks.isNotBlank(name);
	ArgumentChecks.isTrue(compensationDays >= 0.0);

	this.name = name;
	this.compensationDays = compensationDays;
    }

    public String getName() {
	return name;
    }

    public double getCompensationDays() {
	return compensationDays;
    }

    public Set<Contract> getContracts() {
	return new HashSet<>(contracts);
    }

    Set<Contract> _getContracts() {
	return contracts;
    }

    @Override
    public String toString() {
	return "ContractType [name=" + name + ", compensationDays="
		+ compensationDays + "]";
    }

}
