package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

public class DeleteProfessionalGroup implements Command<Void> {

    private ProfessionalGroupRepository repo = Factory.repository
	    .forProfessionalGroup();
    private ContractRepository crepo = Factory.repository.forContract();
    private String name;

    public DeleteProfessionalGroup(String name) {
	ArgumentChecks.isNotBlank(name,
		"The professional group name can not be blanck");

	this.name = name;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<ProfessionalGroup> opg = repo.findByName(name);
	if (opg.isEmpty())
	    throw new BusinessException(
		    "The professional group is not in the data base");

	ProfessionalGroup pg = opg.get();
	if (!crepo.findByProfessionalGroupId(pg.getId()).isEmpty())
	    throw new BusinessException("The professional group has contracts");

	repo.remove(pg);

	return null;
    }

}
