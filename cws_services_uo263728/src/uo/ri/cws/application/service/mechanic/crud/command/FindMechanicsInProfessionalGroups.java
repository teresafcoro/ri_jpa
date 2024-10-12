package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

public class FindMechanicsInProfessionalGroups
	implements Command<List<MechanicDto>> {

    private String name;
    private MechanicRepository repo = Factory.repository.forMechanic();
    private ProfessionalGroupRepository pgrepo = Factory.repository
	    .forProfessionalGroup();

    public FindMechanicsInProfessionalGroups(String name) {
	ArgumentChecks.isNotBlank(name,
		"The professional group name can not be blanck");
	this.name = name;
    }

    @Override
    public List<MechanicDto> execute() throws BusinessException {
	List<MechanicDto> mechanics = new ArrayList<>();

	Optional<ProfessionalGroup> opg = pgrepo.findByName(name);
	if (opg.isPresent())
	    mechanics = DtoAssembler.toMechanicDtoList(
		    repo.findAllInProfessionalGroup(opg.get()));

	return mechanics;
    }

}
