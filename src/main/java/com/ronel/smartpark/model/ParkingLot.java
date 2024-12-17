package com.ronel.smartpark.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "PARKING_LOT")
public class ParkingLot {
	
	@Id
	private String lotId;
	
	
    private String location;
    private int capacity;
    private int occupiedSpaces;
    
    
    
	public ParkingLot() {
	}


	public ParkingLot(String lotId, String location, int capacity, int occupiedSpaces) {
		super();
		this.lotId = lotId;
		this.location = location;
		this.capacity = capacity;
		this.occupiedSpaces = occupiedSpaces;
	}
	
	
	public String getLotId() {
		return lotId;
	}
	public void setLotId(String lotId) {
		this.lotId = lotId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getOccupiedSpaces() {
		return occupiedSpaces;
	}
	public void setOccupiedSpaces(int occupiedSpaces) {
		this.occupiedSpaces = occupiedSpaces;
	}
    
    

}
