package uo.ri.cws.application.service.payroll.create.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

public class GetAllPayrollsForProfessionalGroup
	implements Command<List<PayrollSummaryBLDto>> {

    private String name;
    private List<PayrollSummaryBLDto> payrolls = new ArrayList<>();
    private PayrollRepository repo = Factory.repository.forPayroll();
    private ProfessionalGroupRepository pgrepo = Factory.repository
	    .forProfessionalGroup();
    private ContractRepository crepo = Factory.repository.forContract();

    public GetAllPayrollsForProfessionalGroup(String name) {
	ArgumentChecks.isNotBlank(name,
		"The professional group name can not be blank");
	this.name = name;
    }

    @Override
    public List<PayrollSummaryBLDto> execute() throws BusinessException {
	Optional<ProfessionalGroup> pg = pgrepo.findByName(name);
	if (pg.isEmpty())
	    throw new BusinessException(
		    "The professional group is not in the data base");

	List<Contract> contracts = crepo
		.findByProfessionalGroupId(pg.get().getId());
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
