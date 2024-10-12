package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class DeleteMechanic implements Command<Void> {

    private String mechanicId;
    private MechanicRepository repo = Factory.repository.forMechanic();

    public DeleteMechanic(String mechanicId) {
	ArgumentChecks.isNotBlank(mechanicId,
		"The mechanic id can not be blanck");
	this.mechanicId = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<Mechanic> om = repo.findById(mechanicId);
	BusinessChecks.exists(om,
		"There is no mechanic with that id in the data base");
	Mechanic m = om.get();

	BusinessChecks.isTrue(m.getAssigned().size() == 0,
		"The mechanic has assignations");
	BusinessChecks.isTrue(m.getInterventions().size() == 0,
		"The mechanic has interventions");
	BusinessChecks.isTrue(m.getContractInForce().isEmpty(),
		"The mechanic has a contract in force");
	BusinessChecks.isTrue(m.getTerminatedContracts().isEmpty(),
		"The mechanic has a contracts");

	repo.remove(m);

	return null;
    }

}
