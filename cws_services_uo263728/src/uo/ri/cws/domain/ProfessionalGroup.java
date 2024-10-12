package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class ProfessionalGroup extends BaseEntity {

    private String name;
    private double productivityRate;
    private double trienniumSalary;

    private Set<Contract> contracts = new HashSet<>();

    public ProfessionalGroup() {
    }

    public ProfessionalGroup(String name, double trienniumSalary,
	    double productivityRate) {
	ArgumentChecks.isNotBlank(name);
	ArgumentChecks.isTrue(productivityRate >= 0.0);
	ArgumentChecks.isTrue(trienniumSalary >= 0.0);

	this.name = name;
	this.productivityRate = productivityRate;
	this.trienniumSalary = trienniumSalary;
    }

    public String getName() {
	return name;
    }

    public double getProductivityBonusPercentage() {
	return productivityRate;
    }

    public void setProductivityRate(double productivityRate) {
	ArgumentChecks.isTrue(productivityRate >= 0.0);
	this.productivityRate = productivityRate;
    }

    public double getTrienniumPayment() {
	return trienniumSalary;
    }

    public void setTrienniumSalary(double trienniumSalary) {
	ArgumentChecks.isTrue(trienniumSalary >= 0.0);
	this.trienniumSalary = trienniumSalary;
    }

    public Set<Contract> getContracts() {
	return new HashSet<>(contracts);
    }

    Set<Contract> _getContracts() {
	return contracts;
    }

    @Override
    public String toString() {
	return "ProfessionalGroup [name=" + name + ", productivityRate="
		+ productivityRate + ", trienniumSalary=" + trienniumSalary
		+ "]";
    }

}
