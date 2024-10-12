package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.math.Round;

public class Contract extends BaseEntity {

    public enum ContractState {
	TERMINATED, IN_FORCE
    }

    private LocalDate startDate;
    private LocalDate endDate;
    private double annualWage;
    private double settlement;
    private ContractState state;

    private Mechanic mechanic; // hire
    private Mechanic firedMechanic;
    private ContractType contractType;
    private ProfessionalGroup professionalGroup;
    private Set<Payroll> payrolls = new HashSet<>();

    public Contract() {
    }

    public Contract(Mechanic mechanic, ContractType type,
	    ProfessionalGroup group, double wage) {
	this(mechanic, type, group,
		LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()), wage);
    }

    public Contract(Mechanic mechanic, ContractType type,
	    ProfessionalGroup group, LocalDate endDate, double wage) {
	ArgumentChecks.isNotNull(mechanic);
	ArgumentChecks.isNotNull(type);
	ArgumentChecks.isNotNull(group);
	ArgumentChecks.isNotNull(endDate);
	ArgumentChecks.isTrue(wage >= 0.0);

	this.startDate = LocalDate.now();
	if (endDate.isBefore(startDate))
	    this.endDate = LocalDate.now()
		    .with(TemporalAdjusters.lastDayOfMonth());
	else
	    this.endDate = endDate;
	this.annualWage = wage;
	this.state = ContractState.IN_FORCE;
	Associations.Type.link(this, type);
	Associations.Group.link(this, group);
	Associations.Hire.link(this, mechanic);
    }

    public LocalDate getStartDate() {
	return startDate;
    }

    public void setStartDate(LocalDate startDate) {
	ArgumentChecks.isNotNull(startDate);
	this.startDate = startDate;
    }

    public Optional<LocalDate> getEndDate() {
	return Optional.ofNullable(endDate);
    }

    public double getAnnualBaseWage() {
	return annualWage;
    }

    public double getSettlement() {
	return settlement;
    }

    private void calculateSettlement() {
	if (this.startDate.isBefore(LocalDate.now().minusYears(1))) {
	    int numYears = LocalDate.now().getYear() - this.startDate.getYear();
	    this.settlement = Round.twoCents(this.annualWage / 365
		    * contractType.getCompensationDays() * numYears);
	}
    }

    public ContractState getState() {
	return state;
    }

    public Optional<Mechanic> getMechanic() {
	return Optional.ofNullable(mechanic);
    }

    public Optional<Mechanic> getFiredMechanic() {
	return Optional.ofNullable(firedMechanic);
    }

    void _setMechanic(Mechanic mechanic) {
	this.mechanic = mechanic;
    }

    void _setFiredMechanic(Mechanic firedMechanic) {
	this.firedMechanic = firedMechanic;
    }

    public void setFiredMechanic(Mechanic mechanic) { // Para los tests
	ArgumentChecks.isNotNull(mechanic);
	this.firedMechanic = mechanic;
    }

    public ContractType getContractType() {
	return contractType;
    }

    void _setContractType(ContractType contractType) {
	this.contractType = contractType;
    }

    public ProfessionalGroup getProfessionalGroup() {
	return professionalGroup;
    }

    void _setProfessionalGroup(ProfessionalGroup professionalGroup) {
	this.professionalGroup = professionalGroup;
    }

    public Set<Payroll> getPayrolls() {
	return new HashSet<>(payrolls);
    }

    Set<Payroll> _getPayrolls() {
	return payrolls;
    }

    public void terminate() {
	this.state = ContractState.TERMINATED;
	Associations.Fire.link(this);
	calculateSettlement();
    }

    public double getAmount() {
	if (mechanic != null)
	    return mechanic.getAmount();
	return 0.0;
    }

    @Override
    public String toString() {
	return "Contract [startDate=" + startDate + ", endDate=" + endDate
		+ ", annualWage=" + annualWage + ", settlement=" + settlement
		+ ", state=" + state + ", mechanic=" + mechanic
		+ ", firedMechanic=" + firedMechanic + ", contractType="
		+ contractType + ", professionalGroup=" + professionalGroup
		+ "]";
    }

}
