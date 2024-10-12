package uo.ri.cws.application.service.payroll.create.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class GetAllPayrolls implements Command<List<PayrollSummaryBLDto>> {

    private PayrollRepository repo = Factory.repository.forPayroll();

    @Override
    public List<PayrollSummaryBLDto> execute() throws BusinessException {
	return DtoAssembler.toPayrollSBLDtos(repo.findAll());
    }

}
