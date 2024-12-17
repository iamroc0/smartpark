package com.ronel.smartpark.service;

import com.ronel.smartpark.dto.VehicleRegistrationRequest;
import com.ronel.smartpark.model.ParkingLot;
import com.ronel.smartpark.model.Vehicle;
import com.ronel.smartpark.repository.ParkingLotRepository;
import com.ronel.smartpark.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;


    public Vehicle checkInVehicle(String licensePlate, String lotId) {
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElse(null);
        ParkingLot parkingLot = parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new IllegalArgumentException("Parking lot not found."));


        if (vehicle != null && vehicle.getParkedLot() != null) {
            throw new IllegalStateException("Vehicle is already parked.");
        }


        if (parkingLot.getOccupiedSpaces() >= parkingLot.getCapacity()) {
            throw new IllegalStateException("Parking lot is full.");
        }


        if (vehicle == null) {
            vehicle = new Vehicle();
            vehicle.setLicensePlate(licensePlate);
        }
        vehicle.setParkedLot(parkingLot);
        parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() + 1);


        vehicleRepository.save(vehicle);
        parkingLotRepository.save(parkingLot);

        return vehicle;
    }


    public Vehicle checkOutVehicle(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findById(licensePlate)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found."));

        if (vehicle.getParkedLot() == null) {
            throw new IllegalStateException("Vehicle is not currently parked.");
        }

        ParkingLot parkingLot = vehicle.getParkedLot();
        parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() - 1);
        vehicle.setParkedLot(null);

        vehicleRepository.save(vehicle);
        parkingLotRepository.save(parkingLot);

        return vehicle;
    }


    public Vehicle getVehicleByLicensePlate(String licensePlate) {
        return vehicleRepository.findById(licensePlate)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with license plate: " + licensePlate));
    }


    public List<Vehicle> getAllParkedVehicles() {
        return vehicleRepository.findByParkedLotIsNotNull();
    }


    public List<Vehicle> getVehiclesByParkingLot(String lotId) {
        ParkingLot parkingLot = parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new IllegalArgumentException("Parking lot not found with ID: " + lotId));
        return vehicleRepository.findByParkedLot(parkingLot);
    }
    
    public Vehicle registerVehicle(VehicleRegistrationRequest request) {

        if (!request.getLicensePlate().matches("[A-Z0-9-]+")) {
            throw new IllegalArgumentException("Invalid license plate format.");
        }
        if (!request.getOwnerName().matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Owner name must contain only letters and spaces.");
        }

 
        if (vehicleRepository.existsById(request.getLicensePlate())) {
            throw new IllegalArgumentException("Vehicle already registered.");
        }


        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setType(request.getType());
        vehicle.setOwnerName(request.getOwnerName());
        vehicle.setParkedLot(null); // Not parked yet

        return vehicleRepository.save(vehicle);
    }

    
    public List<Vehicle> getVehiclesByLotId(String lotId) {
        return vehicleRepository.findByParkedLotLotId(lotId);
    }
}
