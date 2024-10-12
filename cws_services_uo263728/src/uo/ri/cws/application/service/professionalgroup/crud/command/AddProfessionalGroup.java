package uo.ri.cws.application.service.professionalgroup.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

public class AddProfessionalGroup implements Command<ProfessionalGroupBLDto> {

    private ProfessionalGroupRepository repo = Factory.repository
	    .forProfessionalGroup();
    private ProfessionalGroupBLDto dto;

    public AddProfessionalGroup(ProfessionalGroupBLDto dto) {
	ArgumentChecks.isNotNull(dto, "The professional group can not be null");
	ArgumentChecks.isNotBlank(dto.name,
		"The professional group name can not be blanck");

	this.dto = dto;
    }

    @Override
    public ProfessionalGroupBLDto execute() throws BusinessException {
	if (repo.findByName(dto.name).isPresent())
	    throw new BusinessException(
		    "The professional group is already in the data base");

	ProfessionalGroup pg = new ProfessionalGroup(dto.name,
		dto.productivityRate, dto.trieniumSalary);
	repo.add(pg);

	return DtoAssembler.toPGBLDto(pg);
    }

}
