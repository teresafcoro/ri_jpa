package uo.ri.cws.application.service.payroll.create.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class GetPayrollDetails implements Command<Optional<PayrollBLDto>> {

    private String id;
    private PayrollRepository repo = Factory.repository.forPayroll();

    public GetPayrollDetails(String id) {
	ArgumentChecks.isNotBlank(id, "The payroll id can not be blank");
	this.id = id;
    }

    @Override
    public Optional<PayrollBLDto> execute() throws BusinessException {
	return DtoAssembler.toOpPayrollBLDto(repo.findById(id));
    }

}
