package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Vehicle extends BaseEntity {

    private String plateNumber;
    private String make;
    private String model;

    // accidental attribute
    private Client client;
    private VehicleType vehicleType;
    private Set<WorkOrder> workOrders = new HashSet<>();

    public Vehicle() {
    }

    public Vehicle(String plateNumber) {
	this(plateNumber, "no-make", "no-model");
    }

    public Vehicle(String plateNumber, String make, String model) {
	ArgumentChecks.isNotBlank(plateNumber);
	ArgumentChecks.isNotBlank(make);
	ArgumentChecks.isNotBlank(model);

	this.plateNumber = plateNumber;
	this.make = make;
	this.model = model;
    }

    public String getPlateNumber() {
	return plateNumber;
    }

    public String getMake() {
	return make;
    }

    public String getModel() {
	return model;
    }

    public Client getClient() {
	return client;
    }

    void _setClient(Client client) {
	this.client = client;
    }

    public VehicleType getVehicleType() {
	return vehicleType;
    }

    void _setVehicleType(VehicleType vehicleType) {
	this.vehicleType = vehicleType;
    }

    public Set<WorkOrder> getWorkOrders() {
	return new HashSet<>(workOrders);
    }

    Set<WorkOrder> _getWorkOrders() {
	return workOrders;
    }

    @Override
    public String toString() {
	return "Vehicle [plateNumber=" + plateNumber + ", make=" + make
		+ ", model=" + model + "]";
    }

}
