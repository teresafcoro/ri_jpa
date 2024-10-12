package uo.ri.cws.application.service.contract.impl;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService;

public class ContractServiceImpl implements ContractService {

    @Override
    public ContractDto addContract(ContractDto c) throws BusinessException {
	return null;
    }

    @Override
    public void updateContract(ContractDto dto) throws BusinessException {
    }

    @Override
    public void deleteContract(String id) throws BusinessException {
    }

    @Override
    public void terminateContract(String contractId) throws BusinessException {
    }

    @Override
    public Optional<ContractDto> findContractById(String id)
	    throws BusinessException {
	return null;
    }

    @Override
    public List<ContractSummaryDto> findContractsByMechanic(String mechanicDni)
	    throws BusinessException {
	return null;
    }

    @Override
    public List<ContractSummaryDto> findAllContracts()
	    throws BusinessException {
	return null;
    }

}
