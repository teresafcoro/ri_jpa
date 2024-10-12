package uo.ri.ui.manager.payroll.action;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollBLDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListPayrollDetailAction implements Action {

    @Override
    public void execute() throws Exception {
	// Get info
	String id = Console.readString("Payroll id  ");

	// Process
	PayrollService ps = Factory.service.forPayrollService();
	Optional<PayrollBLDto> payroll = ps.getPayrollDetails(id);

	if (payroll.isEmpty())
	    Console.println("\nPayroll does not exist\n");
	else {
	    // Print result
	    Console.println(String.format("Details Payroll %s", id));
	    Printer.printPayroll(payroll.get());
	}
    }

}
