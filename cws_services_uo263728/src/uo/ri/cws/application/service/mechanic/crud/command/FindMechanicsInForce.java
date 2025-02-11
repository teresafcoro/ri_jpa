package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindMechanicsInForce implements Command<List<MechanicDto>> {

    private MechanicRepository repo = Factory.repository.forMechanic();

    @Override
    public List<MechanicDto> execute() throws BusinessException {
	return DtoAssembler.toMechanicDtoList(repo.findAllInForce());
    }

}
