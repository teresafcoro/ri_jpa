package uo.ri.cws.application.service.payroll.create.command;

import java.time.LocalDate;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Payroll;

public class GeneratePayrolls implements Command<Void> {

    private LocalDate date;
    private PayrollRepository repo = Factory.repository.forPayroll();
    private ContractRepository crepo = Factory.repository.forContract();

    public GeneratePayrolls() {
	this.date = LocalDate.now();
    }

    public GeneratePayrolls(LocalDate present) {
	if (present != null)
	    this.date = present;
	else
	    this.date = LocalDate.now();
    }

    @Override
    public Void execute() throws BusinessException {
	if (!repo.findCurrentMonthPayrolls().isEmpty())
	    return null;

	List<Contract> contracts = crepo.findAllInForceThisMonth(date);
	for (Contract contract : contracts) {
	    Payroll payroll = new Payroll(contract, date);
	    repo.add(payroll);
	}

	return null;
    }

}
