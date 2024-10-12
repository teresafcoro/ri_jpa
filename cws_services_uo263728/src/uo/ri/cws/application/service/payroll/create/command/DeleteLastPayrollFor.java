package uo.ri.cws.application.service.payroll.create.command;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;

public class DeleteLastPayrollFor implements Command<Void> {

    private PayrollRepository repo = Factory.repository.forPayroll();
    private MechanicRepository mrepo = Factory.repository.forMechanic();
    private ContractRepository crepo = Factory.repository.forContract();
    private String id;

    public DeleteLastPayrollFor(String id) {
	ArgumentChecks.isNotBlank(id, "The mechanic id can not be blank");
	this.id = id;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<Mechanic> mechanic = mrepo.findById(id);
	if (mechanic.isEmpty())
	    throw new BusinessException(
		    "The mechanic does not exist in the data base");

	List<Contract> contracts = crepo.findByMechanicId(id);
	for (Contract contract : contracts) {
	    Optional<Payroll> payroll = repo
		    .findCurrentMonthByContractId(contract.getId());
	    if (payroll.isPresent())
		repo.remove(payroll.get());
	}

	return null;
    }

}
