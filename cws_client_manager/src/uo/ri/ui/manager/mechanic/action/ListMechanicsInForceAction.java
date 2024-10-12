package uo.ri.ui.manager.mechanic.action;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListMechanicsInForceAction implements Action {

    @Override
    public void execute() throws Exception {
	Console.println("\nList of mechanics with contract in force\n");

	MechanicCrudService as = Factory.service.forMechanicCrudService();
	List<MechanicDto> mechanics = as.findMechanicsInForce();

	mechanics.forEach(m -> Printer.printMechanic(m));
    }

}
