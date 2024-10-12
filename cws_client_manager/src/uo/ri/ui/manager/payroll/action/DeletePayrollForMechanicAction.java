package uo.ri.ui.manager.payroll.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class DeletePayrollForMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {
	// Get info
	String dni = Console.readString("Mechanic dni ");

	PayrollService ps = Factory.service.forPayrollService();
	ps.deleteLastPayrollFor(dni);

	// Print result
	Console.println(String.format(
		"Last payroll for mechanic %s successfully deleted", dni));
    }

}
