package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class PayrollJpaRepository extends BaseJpaRepository<Payroll>
	implements PayrollRepository {

    @Override
    public List<Payroll> findByContract(String contractId) {
	return Jpa.getManager()
		.createNamedQuery("Payroll.findByContract", Payroll.class)
		.setParameter(1, contractId).getResultList();
    }

    @Override
    public List<Payroll> findCurrentMonthPayrolls() {
	LocalDate minDate = LocalDate.now()
		.with(TemporalAdjusters.firstDayOfMonth());
	LocalDate maxDate = LocalDate.now()
		.with(TemporalAdjusters.lastDayOfMonth());
	return Jpa.getManager()
		.createNamedQuery("Payroll.findCurrentMonthPayrolls",
			Payroll.class)
		.setParameter(1, minDate).setParameter(2, maxDate)
		.getResultList();
    }

    @Override
    public Optional<Payroll> findCurrentMonthByContractId(String contractId) {
	LocalDate minDate = LocalDate.now()
		.with(TemporalAdjusters.firstDayOfMonth());
	LocalDate maxDate = LocalDate.now()
		.with(TemporalAdjusters.lastDayOfMonth());
	return Jpa.getManager()
		.createNamedQuery("Payroll.findCurrentMonthByContractId",
			Payroll.class)
		.setParameter(1, minDate).setParameter(2, maxDate)
		.setParameter(3, contractId).getResultStream().findFirst();
    }

}
