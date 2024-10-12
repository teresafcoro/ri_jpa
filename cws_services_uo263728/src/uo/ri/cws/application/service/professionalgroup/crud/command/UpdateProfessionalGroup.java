package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

public class UpdateProfessionalGroup implements Command<Void> {

    private ProfessionalGroupRepository repo = Factory.repository
	    .forProfessionalGroup();
    private ProfessionalGroupBLDto dto;

    public UpdateProfessionalGroup(ProfessionalGroupBLDto dto) {
	ArgumentChecks.isNotNull(dto, "The professional group can not be null");
	ArgumentChecks.isNotBlank(dto.name,
		"The professional group name can not be blanck");
	ArgumentChecks.isTrue(dto.productivityRate >= 0,
		"The professional group productivity rate can not be negative");
	ArgumentChecks.isTrue(dto.trieniumSalary >= 0,
		"The professional group trienium salary can not be negative");

	this.dto = dto;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<ProfessionalGroup> opg = repo.findByName(dto.name);
	BusinessChecks.exists(opg,
		"The professional group name is not in the data base");

	ProfessionalGroup pg = opg.get();
	BusinessChecks.isTrue(dto.version == pg.getVersion(),
		"Working on stale data");

	pg.setProductivityRate(dto.productivityRate);
	pg.setTrienniumSalary(dto.trieniumSalary);

	return null;
    }

}
