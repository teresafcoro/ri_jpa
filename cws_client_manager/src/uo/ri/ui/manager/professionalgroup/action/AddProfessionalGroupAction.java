package uo.ri.ui.manager.professionalgroup.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class AddProfessionalGroupAction implements Action {

    @Override
    public void execute() throws Exception {
	ProfessionalGroupBLDto pg = new ProfessionalGroupBLDto();
	pg.name = Console.readString("Name ");
	pg.trieniumSalary = Console.readDouble("Triennium Salary ");
	pg.productivityRate = Console.readDouble("Productivity rate ");

	ProfessionalGroupService pgs = Factory.service
		.forProfessionalGroupService();
	ProfessionalGroupBLDto result = pgs.addProfessionalGroup(pg);

	Console.println("New Professional Group succesfully added");
	Printer.printProfessionalGroup(result);
    }

}
