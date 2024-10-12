package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class FindProfessionalGroupByName
	implements Command<Optional<ProfessionalGroupBLDto>> {

    private ProfessionalGroupRepository repo = Factory.repository
	    .forProfessionalGroup();
    private String name;

    public FindProfessionalGroupByName(String name) {
	ArgumentChecks.isNotBlank(name,
		"The professional group name can not be blanck");

	this.name = name;
    }

    @Override
    public Optional<ProfessionalGroupBLDto> execute() throws BusinessException {
	return DtoAssembler.toOpPGBLDto(repo.findByName(name));
    }

}
