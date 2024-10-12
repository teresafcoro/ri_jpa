package uo.ri.ui.manager.mechanic.action;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListMechanicsInProfessionalGroupsAction implements Action {

    @Override
    public void execute() throws Exception {
	String name = Console.readString("Professional group name ");

	Console.println("\nList of mechanics in professional group\n");
	MechanicCrudService as = Factory.service.forMechanicCrudService();
	List<MechanicDto> mechanics = as
		.findMechanicsInProfessionalGroups(name);

	mechanics.forEach(m -> Printer.printMechanic(m));
    }

}
