package uo.ri.cws.application.service.payroll.create.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;

public class DeleteLastPayrolls implements Command<Void> {

    private PayrollRepository repo = Factory.repository.forPayroll();

    @Override
    public Void execute() throws BusinessException {
	List<Payroll> payrolls = repo.findCurrentMonthPayrolls();
	for (Payroll payroll : payrolls)
	    repo.remove(payroll);
	return null;
    }

}
