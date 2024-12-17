package com.ronel.smartpark.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ronel.smartpark.model.ParkingLot;
import com.ronel.smartpark.repository.ParkingLotRepository;

@Service
public class ParkingLotService {
	
	private final ParkingLotRepository parkingLotRepository;
	
	public ParkingLotService(ParkingLotRepository parkingLotRepository) {
	    this.parkingLotRepository = parkingLotRepository;
	}
	
	public ParkingLot createParkingLot(ParkingLot parkingLot) {
	    return parkingLotRepository.save(parkingLot);
	}
	
	public Optional<ParkingLot> getParkingLotById(String lotId) {
	    return parkingLotRepository.findById(lotId);
	}
	
	 public ParkingLot updateParkingLot(String lotId, ParkingLot updatedParkingLot) {
	        Optional<ParkingLot> existingParkingLot = parkingLotRepository.findById(lotId);

	        if (existingParkingLot.isPresent()) {
	            ParkingLot parkingLot = existingParkingLot.get();
	            parkingLot.setLocation(updatedParkingLot.getLocation());
	            parkingLot.setCapacity(updatedParkingLot.getCapacity());
	            // ... other fields to update
	            return parkingLotRepository.save(parkingLot);
	        } else {
	        	throw new RuntimeException("Parking lot with ID " + lotId + " not found");
	        }
	    }
	
	public void deleteParkingLot(String lotId) {
	    parkingLotRepository.deleteById(lotId);
	}

}
