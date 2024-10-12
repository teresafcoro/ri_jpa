package uo.ri.ui.manager.professionalgroup.action;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListProfessionalGroupByNameAction implements Action {

    @Override
    public void execute() throws BusinessException {
	String name = Console.readString("Professional group name ");

	ProfessionalGroupService pgs = Factory.service
		.forProfessionalGroupService();
	Optional<ProfessionalGroupBLDto> pg = pgs
		.findProfessionalGroupByName(name);

	if (pg.isEmpty())
	    Console.println("\nProfessional group does not exist\n");
	else
	    Printer.printProfessionalGroup(pg.get());
    }

}
