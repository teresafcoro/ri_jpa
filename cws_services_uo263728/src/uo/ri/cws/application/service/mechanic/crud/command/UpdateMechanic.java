package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class UpdateMechanic implements Command<Void> {

    private MechanicDto dto;
    private MechanicRepository repo = Factory.repository.forMechanic();

    public UpdateMechanic(MechanicDto dto) {
	ArgumentChecks.isNotNull(dto, "The mechanic can not be null");
	ArgumentChecks.isNotBlank(dto.dni,
		"The mechanic dni can not be blanck");
	ArgumentChecks.isNotBlank(dto.name,
		"The mechanic name can not be blanck");
	ArgumentChecks.isNotBlank(dto.surname,
		"The mechanic surname can not be blanck");

	this.dto = dto;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<Mechanic> om = repo.findByDni(dto.dni);
	BusinessChecks.exists(om, "The mechanic dni is not in the data base");

	Mechanic m = om.get();
	BusinessChecks.isTrue(dto.version == m.getVersion(),
		"Working on stale data");

	m.setName(dto.name);
	m.setSurname(dto.surname);

	return null;
    }

}
