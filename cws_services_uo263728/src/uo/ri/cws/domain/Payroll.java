package uo.ri.cws.domain;

import java.time.LocalDate;

import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.math.Round;

public class Payroll extends BaseEntity {

    private LocalDate date;
    private double bonus;
    private double incomeTax;
    private double monthlyWage;
    private double nic;
    private double productivityBonus;
    private double trienniumPayment;

    private Contract contract;

    public Payroll() {
    }

    public Payroll(Contract contract) {
	this(contract, LocalDate.now());
    }

    public Payroll(Contract contract, LocalDate date) {
	ArgumentChecks.isNotNull(contract);
	ArgumentChecks.isNotNull(date);

	Associations.Run.link(this, contract);

	this.date = date;
	calculateMonthlyWage();
	calculateProductivityBonus();
	calculateTrienniumPayment();
	calculateBonus();
	calculateIncomeTax();
	calculateNIC();
    }

    // Constructor para el test de la extensión ContractTest
    public Payroll(Contract contract, LocalDate date, double monthlyWage,
	    double extra, double productivity, double trienniums, double tax,
	    double nic) {
	ArgumentChecks.isNotNull(contract);
	ArgumentChecks.isNotNull(date);
	ArgumentChecks.isTrue(monthlyWage >= 0.0);
	ArgumentChecks.isTrue(extra >= 0.0);
	ArgumentChecks.isTrue(productivity >= 0.0);
	ArgumentChecks.isTrue(trienniums >= 0.0);
	ArgumentChecks.isTrue(tax >= 0.0);
	ArgumentChecks.isTrue(nic >= 0.0);

	this.date = date;
	this.monthlyWage = monthlyWage;
	this.bonus = extra;
	this.productivityBonus = productivity;
	this.trienniumPayment = trienniums;
	this.incomeTax = tax;
	this.nic = nic;

	Associations.Run.link(this, contract);
    }

    public LocalDate getDate() {
	return date;
    }

    public double getBonus() {
	return bonus;
    }

    private void calculateMonthlyWage() {
	monthlyWage = Round.twoCents(contract.getAnnualBaseWage() / 14);
    }

    private void calculateBonus() {
	bonus = getBonusFor(date) ? monthlyWage : 0.0;
    }

    private boolean getBonusFor(LocalDate date) {
	return date.getMonthValue() == 6 | date.getMonthValue() == 12;
    }

    private void calculateProductivityBonus() {
	double productivityBonus = contract.getAmount();
	productivityBonus *= contract.getProfessionalGroup()
		.getProductivityBonusPercentage() / 100;
	this.productivityBonus = Round.twoCents(productivityBonus);
    }

    private void calculateTrienniumPayment() {
	int triennium = 0;
	int year = getDate().getYear() - 3;
	while (year > 2000) {
	    if (contract.getState() == ContractState.IN_FORCE
		    & contract.getStartDate()
			    .isBefore(LocalDate.of(year,
				    getDate().getMonthValue(),
				    getDate().getDayOfMonth())))
		triennium++;
	    year -= 3;
	}
	this.trienniumPayment = Round.twoCents(triennium
		* contract.getProfessionalGroup().getTrienniumPayment());
    }

    public double getIncomeTax() {
	return incomeTax;
    }

    private void calculateIncomeTax() {
	double grossWage = calculateGrossWage();
	this.incomeTax = Round.twoCents(
		grossWage - grossWage * getRate(contract.getAnnualBaseWage()));
    }

    private double calculateGrossWage() {
	return monthlyWage + bonus + productivityBonus + trienniumPayment;
    }

    private double getRate(double annualBaseWage) {
	double rate = 0.0;
	if (annualBaseWage >= 0 & annualBaseWage < 12450)
	    rate = 1 - 19.0 / 100;
	if (annualBaseWage >= 12450 & annualBaseWage < 20200)
	    rate = 1 - 24.0 / 100;
	if (annualBaseWage >= 20200 & annualBaseWage < 35200)
	    rate = 1 - 30.0 / 100;
	if (annualBaseWage >= 35200 & annualBaseWage < 60000)
	    rate = 1 - 37.0 / 100;
	if (annualBaseWage >= 60000 & annualBaseWage < 300000)
	    rate = 1 - 45.0 / 100;
	if (annualBaseWage >= 300000)
	    rate = 1 - 47.0 / 100;
	return rate;
    }

    public double getMonthlyWage() {
	return monthlyWage;
    }

    public double getNIC() {
	return nic;
    }

    private void calculateNIC() {
	double salarioBaseAnualProrrateado = contract.getAnnualBaseWage() / 12;
	this.nic = Round.twoCents(salarioBaseAnualProrrateado
		- salarioBaseAnualProrrateado * (1 - 5.0 / 100));
    }

    public double getProductivityBonus() {
	return productivityBonus;
    }

    public double getTrienniumPayment() {
	return trienniumPayment;
    }

    public double getNetWage() {
	return Round.twoCents(calculateGrossWage() - calculateDeductions());
    }

    public double calculateDeductions() {
	return incomeTax + nic;
    }

    public Contract getContract() {
	return contract;
    }

    void _setContract(Contract contract) {
	this.contract = contract;
    }

    @Override
    public String toString() {
	return "Payroll [date=" + date + ", bonus=" + bonus + ", incomeTax="
		+ incomeTax + ", monthlyWage=" + monthlyWage + ", nic=" + nic
		+ ", productivityBonus=" + productivityBonus
		+ ", trienniumPayment=" + trienniumPayment + "]";
    }

}
