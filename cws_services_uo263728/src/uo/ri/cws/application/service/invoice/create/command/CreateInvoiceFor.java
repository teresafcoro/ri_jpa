package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.cws.domain.WorkOrder.WorkOrderState;
import uo.ri.util.assertion.ArgumentChecks;

public class CreateInvoiceFor implements Command<InvoiceDto> {

    private List<String> workOrderIds;
    private WorkOrderRepository wrkrsRepo = Factory.repository.forWorkOrder();
    private InvoiceRepository invsRepo = Factory.repository.forInvoice();

    public CreateInvoiceFor(List<String> workOrderIds) {
	ArgumentChecks.isNotNull(workOrderIds, "WorkOrder ids can not be null");
	ArgumentChecks.isFalse(workOrderIds.isEmpty(),
		"WorkOrder ids can not be empty");
	for (String id : workOrderIds)
	    ArgumentChecks.isNotBlank(id, "WorkOrder id can not be blank");
	this.workOrderIds = workOrderIds;
    }

    @Override
    public InvoiceDto execute() throws BusinessException {
	Long nextNumber = invsRepo.getNextInvoiceNumber();

	List<WorkOrder> workOrders = wrkrsRepo.findByIds(workOrderIds);

	BusinessChecks.isTrue(workOrderIds.size() == workOrders.size(),
		"Not all the workorders exist");

	BusinessChecks.isTrue(allAreFinished(workOrders),
		"Not all the workorders are finished");

	Invoice i = new Invoice(nextNumber, workOrders);
	invsRepo.add(i);

	return DtoAssembler.toDto(i);
    }

    private boolean allAreFinished(List<WorkOrder> workOrders) {
	for (WorkOrder workOrder : workOrders) {
	    if (!workOrder.getState().equals(WorkOrderState.FINISHED))
		return false;
	}
	return true;
    }

}
