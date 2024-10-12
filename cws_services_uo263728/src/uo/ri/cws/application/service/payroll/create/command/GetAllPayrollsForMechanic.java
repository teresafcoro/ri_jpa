package uo.ri.cws.application.service.payroll.create.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;

public class GetAllPayrollsForMechanic
	implements Command<List<PayrollSummaryBLDto>> {

    private PayrollRepository repo = Factory.repository.forPayroll();
    private MechanicRepository mrepo = Factory.repository.forMechanic();
    private ContractRepository crepo = Factory.repository.forContract();
    private String id;
    private List<PayrollSummaryBLDto> payrolls = new ArrayList<>();

    public GetAllPayrollsForMechanic(String id) {
	ArgumentChecks.isNotBlank(id, "The mechanic id can not be blank");
	this.id = id;
    }

    @Override
    public List<PayrollSummaryBLDto> execute() throws BusinessException {
	List<PayrollSummaryBLDto> payrolls = new ArrayList<>();

	if (mrepo.findById(id).isEmpty())
	    throw new BusinessException("The mechanic is not in the data base");

	List<Contract> contracts = crepo.findByMechanicId(id);
	for (Contract contract : contracts)
	    addPayrollsByContract(contract.getId());

	return payrolls;
    }

    private void addPayrollsByContract(String id) {
	List<Payroll> payrollsContract = repo.findByContract(id);
	for (Payroll payroll : payrollsContract)
	    payrolls.add(DtoAssembler.toPayrollSBLDto(payroll));
    }

}
