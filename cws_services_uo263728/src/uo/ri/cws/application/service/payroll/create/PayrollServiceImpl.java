package uo.ri.cws.application.service.payroll.create;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.create.command.DeleteLastPayrollFor;
import uo.ri.cws.application.service.payroll.create.command.DeleteLastPayrolls;
import uo.ri.cws.application.service.payroll.create.command.GeneratePayrolls;
import uo.ri.cws.application.service.payroll.create.command.GetAllPayrolls;
import uo.ri.cws.application.service.payroll.create.command.GetAllPayrollsForMechanic;
import uo.ri.cws.application.service.payroll.create.command.GetAllPayrollsForProfessionalGroup;
import uo.ri.cws.application.service.payroll.create.command.GetPayrollDetails;
import uo.ri.cws.application.util.command.CommandExecutor;

public class PayrollServiceImpl implements PayrollService {

    private CommandExecutor executor = Factory.executor.forExecutor();

    @Override
    public void generatePayrolls() throws BusinessException {
	executor.execute(new GeneratePayrolls());
    }

    @Override
    public void generatePayrolls(LocalDate present) throws BusinessException {
	executor.execute(new GeneratePayrolls(present));
    }

    @Override
    public void deleteLastPayrollFor(String mechanicId)
	    throws BusinessException {
	executor.execute(new DeleteLastPayrollFor(mechanicId));
    }

    @Override
    public void deleteLastPayrolls() throws BusinessException {
	executor.execute(new DeleteLastPayrolls());
    }

    @Override
    public Optional<PayrollBLDto> getPayrollDetails(String id)
	    throws BusinessException {
	return executor.execute(new GetPayrollDetails(id));
    }

    @Override
    public List<PayrollSummaryBLDto> getAllPayrolls() throws BusinessException {
	return executor.execute(new GetAllPayrolls());
    }

    @Override
    public List<PayrollSummaryBLDto> getAllPayrollsForMechanic(String id)
	    throws BusinessException {
	return executor.execute(new GetAllPayrollsForMechanic(id));
    }

    @Override
    public List<PayrollSummaryBLDto> getAllPayrollsForProfessionalGroup(
	    String name) throws BusinessException {
	return executor.execute(new GetAllPayrollsForProfessionalGroup(name));
    }

}
