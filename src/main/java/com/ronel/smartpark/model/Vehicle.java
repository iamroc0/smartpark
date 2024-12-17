package com.ronel.smartpark.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "VEHICLE")
public class Vehicle {
    @Id
    private String licensePlate;
    private String type;
    private String ownerName;

    @ManyToOne
    @JoinColumn(name = "parked_lot_id")
    private ParkingLot parkedLot;

	public Vehicle() {
		super();
	}

	public Vehicle(String licensePlate, String type, String ownerName, ParkingLot parkedLot) {
		super();
		this.licensePlate = licensePlate;
		this.type = type;
		this.ownerName = ownerName;
		this.parkedLot = parkedLot;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public ParkingLot getParkedLot() {
		return parkedLot;
	}

	public void setParkedLot(ParkingLot parkedLot) {
		this.parkedLot = parkedLot;
	}

    
}