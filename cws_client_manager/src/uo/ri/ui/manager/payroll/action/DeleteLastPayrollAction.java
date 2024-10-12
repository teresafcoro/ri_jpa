package uo.ri.ui.manager.payroll.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class DeleteLastPayrollAction implements Action {

    @Override
    public void execute() throws BusinessException {
	PayrollService ps = Factory.service.forPayrollService();
	ps.deleteLastPayrolls();

	// Print result
	Console.println("Last payroll successfully deleted");
    }

}
