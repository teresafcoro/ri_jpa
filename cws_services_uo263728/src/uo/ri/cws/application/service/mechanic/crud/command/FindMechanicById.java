package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class FindMechanicById implements Command<Optional<MechanicDto>> {

    private String id;
    private MechanicRepository repo = Factory.repository.forMechanic();

    public FindMechanicById(String id) {
	ArgumentChecks.isNotBlank(id, "The mechanic id can not be blanck");
	this.id = id;
    }

    @Override
    public Optional<MechanicDto> execute() throws BusinessException {
	return repo.findById(id).map(m -> DtoAssembler.toDto(m));
    }

}
