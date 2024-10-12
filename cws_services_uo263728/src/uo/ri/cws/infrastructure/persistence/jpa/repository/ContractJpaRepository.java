package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class ContractJpaRepository extends BaseJpaRepository<Contract>
	implements ContractRepository {

    @Override
    public List<Contract> findAllInForce() {
	return Jpa.getManager()
		.createNamedQuery("Contract.findAllInForce", Contract.class)
		.setParameter(1, ContractState.IN_FORCE).getResultList();
    }

    @Override
    public List<Contract> findByMechanicId(String id) {
	return Jpa.getManager()
		.createNamedQuery("Contract.findByMechanicId", Contract.class)
		.setParameter(1, id).getResultList();
    }

    @Override
    public List<Contract> findByProfessionalGroupId(String id) {
	return Jpa.getManager()
		.createNamedQuery("Contract.findByProfessionalGroupId",
			Contract.class)
		.setParameter(1, id).getResultList();
    }

    @Override
    public List<Contract> findByContractTypeId(String id2Del) {
	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public List<Contract> findAllInForceThisMonth(LocalDate present) {
	LocalDate minDate = present.with(TemporalAdjusters.firstDayOfMonth());
	LocalDate maxDate = present.with(TemporalAdjusters.lastDayOfMonth());
	return Jpa.getManager()
		.createNamedQuery("Contract.findAllInForceThisMonth",
			Contract.class)
		.setParameter(1, ContractState.IN_FORCE)
		.setParameter(2, minDate).setParameter(3, maxDate)
		.getResultList();
    }

}
