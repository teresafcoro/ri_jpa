package uo.ri.cws.application.service.contracttype;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;

public interface ContractTypeService {

    /**
     * 
     * @param dto, the id field is meaningless for this operation
     * @return dto with id
     * @throws IllegalArgumentException if - arg is null - name is null or empty
     *                                  - the number of compensation days is
     *                                  negative
     * @throws BusinessException        if: - another type of contract with the
     *                                  same name already exists
     */
    ContractTypeDto addContractType(ContractTypeDto dto)
	    throws BusinessException;

    /**
     * @param name of the contract type
     * @throws IllegalArgumentException if - name is null or empty
     * @throws BusinessException        if - the contract type does not exist,
     *                                  or - there are contracts registered with
     *                                  the contract type
     */
    void deleteContractType(String name) throws BusinessException;

    /**
     * Updates just the compensation days
     * 
     * @param dto
     * @throws IllegalArgumentException if - arg is null - nanme is null or
     *                                  empty - the number of compensation days
     *                                  is negative
     * @throws BusinessException        if: - the contract type does not exist,
     *                                  or
     */
    void updateContractType(ContractTypeDto dto) throws BusinessException;

    /**
     * @param name, of the contract type
     * @throws IllegalArgumentException if - name is null or empty
     * @return the dto with all the fields set, or empty if does not exist the
     *         contract type
     * @throws BusinessException DOES NOT
     */
    Optional<ContractTypeDto> findContractTypeByName(String name)
	    throws BusinessException;

    /**
     * @return a list with all the contract types registered, or an empty list
     *         if ther are none
     * @throws BusinessException DOES NOT
     */
    List<ContractTypeDto> findAllContractTypes() throws BusinessException;

    public class ContractTypeDto {

	public String id;
	public Long version;
	public String name;
	public double compensationDays;

    }

}
