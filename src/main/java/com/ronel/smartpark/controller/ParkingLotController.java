package com.ronel.smartpark.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ronel.smartpark.model.ParkingLot;
import com.ronel.smartpark.model.Vehicle;
import com.ronel.smartpark.repository.ParkingLotRepository;
import com.ronel.smartpark.service.ParkingLotService;
import com.ronel.smartpark.service.VehicleService;

@RestController
@RequestMapping("/parkingLots")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;
    
    private final VehicleService vehicleService;

    public ParkingLotController(ParkingLotService parkingLotService, VehicleService vehicleService) {
	    this.parkingLotService = parkingLotService;
		this.vehicleService = vehicleService;
	}

    @PostMapping("/register")
    public ParkingLot createParkingLot(@RequestBody ParkingLot parkingLot) {
        return parkingLotService.createParkingLot(parkingLot);
    }
    
    @GetMapping("/{lotId}/vehicles")
    public ResponseEntity<List<Vehicle>> getVehiclesByLotId(@PathVariable String lotId) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByLotId(lotId);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{lotId}")
    public ResponseEntity<ParkingLot> getParkingLotById(@PathVariable String lotId) {
        Optional<ParkingLot> parkingLotOptional = parkingLotService.getParkingLotById(lotId);
        return parkingLotOptional.map(ResponseEntity::ok)
                                 .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{lotId}")
    public ResponseEntity<ParkingLot> updateParkingLot(@PathVariable String lotId, @RequestBody ParkingLot parkingLot) {
        ParkingLot updatedParkingLot = parkingLotService.updateParkingLot(lotId, parkingLot);
        return ResponseEntity.ok(updatedParkingLot);
    }

    @DeleteMapping("/{lotId}")
    public ResponseEntity<Void> deleteParkingLot(@PathVariable String lotId) {
        parkingLotService.deleteParkingLot(lotId);
        return ResponseEntity.noContent().build();
    }
}
