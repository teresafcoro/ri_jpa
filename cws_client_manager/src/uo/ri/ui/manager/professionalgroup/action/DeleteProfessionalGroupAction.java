package uo.ri.ui.manager.professionalgroup.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class DeleteProfessionalGroupAction implements Action {

    @Override
    public void execute() throws Exception {
	String name = Console.readString("Professional group name ");

	ProfessionalGroupService pgs = Factory.service
		.forProfessionalGroupService();
	pgs.deleteProfessionalGroup(name);

	Console.print("Professional group successfully deleted");
    }

}
