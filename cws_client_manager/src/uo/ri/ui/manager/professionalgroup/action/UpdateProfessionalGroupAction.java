package uo.ri.ui.manager.professionalgroup.action;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class UpdateProfessionalGroupAction implements Action {

    @Override
    public void execute() throws BusinessException {
	String name = Console.readString("Professional group name ");

	ProfessionalGroupService pgs = Factory.service
		.forProfessionalGroupService();
	Optional<ProfessionalGroupBLDto> pg = pgs
		.findProfessionalGroupByName(name);
	if (!pg.isPresent()) {
	    throw new BusinessException(
		    "There is no professional group with that name");
	}

	ProfessionalGroupBLDto dto = pg.get();
	Printer.printProfessionalGroup(dto);

	dto.trieniumSalary = Console
		.readDouble("Professional group triennium salary ");
	dto.productivityRate = Console
		.readDouble("Professional group productivity salary ");

	pgs.updateProfessionalGroup(dto);

	Console.print("Professional group successfully updated");
    }

}
