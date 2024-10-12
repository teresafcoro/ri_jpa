package uo.ri.ui.manager.professionalgroup.action;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListAllProfessionalGroupAction implements Action {

    @Override
    public void execute() throws BusinessException {
	Console.println("\nList of professional groups \n");
	ProfessionalGroupService pgs = Factory.service
		.forProfessionalGroupService();
	List<ProfessionalGroupBLDto> professionalGroups = pgs
		.findAllProfessionalGroups();

	professionalGroups.forEach(p -> Printer.printProfessionalGroup(p));
    }

}
