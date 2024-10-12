package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class VehicleType extends BaseEntity {

    private String name;
    private double pricePerHour = 0.0;

    // accidental attributes
    private Set<Vehicle> vehicles = new HashSet<>();

    public VehicleType() {
    }

    public VehicleType(String name) {
	this(name, 0.0);
    }

    public VehicleType(String name, double pricePerHour) {
	ArgumentChecks.isNotBlank(name);
	ArgumentChecks.isTrue(pricePerHour >= 0.0);

	this.name = name;
	this.pricePerHour = pricePerHour;
    }

    public String getName() {
	return name;
    }

    public double getPricePerHour() {
	return pricePerHour;
    }

    public Set<Vehicle> getVehicles() {
	return new HashSet<>(vehicles);
    }

    Set<Vehicle> _getVehicles() {
	return vehicles;
    }

    @Override
    public String toString() {
	return "VehicleType [name=" + name + ", pricePerHour=" + pricePerHour
		+ "]";
    }

}
