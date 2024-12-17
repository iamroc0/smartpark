package com.ronel.smartpark.controller;

import com.ronel.smartpark.model.Vehicle;
import com.ronel.smartpark.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ronel.smartpark.dto.VehicleCheckInRequest;
import com.ronel.smartpark.dto.VehicleCheckOutRequest;
import com.ronel.smartpark.dto.VehicleRegistrationRequest;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;
    
    
    
    @PostMapping("/register")
    public ResponseEntity<Vehicle> registerVehicle(@RequestBody VehicleRegistrationRequest request) {
        try {
            Vehicle vehicle = vehicleService.registerVehicle(request);
            return ResponseEntity.ok(vehicle);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/checkin")
    public ResponseEntity<?> checkInVehicle(@RequestBody VehicleCheckInRequest request) {
        try {
            Vehicle vehicle = vehicleService.checkInVehicle(request.getLicensePlate(), request.getLotId());
            return ResponseEntity.ok(vehicle);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/checkout")
    public ResponseEntity<?> checkOutVehicle(@RequestBody VehicleCheckOutRequest request) {
        try {
            Vehicle vehicle = vehicleService.checkOutVehicle(request.getLicensePlate());
            return ResponseEntity.ok(vehicle);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{licensePlate}")
    public ResponseEntity<?> getVehicleByLicensePlate(@PathVariable String licensePlate) {
        try {
            Vehicle vehicle = vehicleService.getVehicleByLicensePlate(licensePlate);
            return ResponseEntity.ok(vehicle);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/parked")
    public ResponseEntity<List<Vehicle>> getAllParkedVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllParkedVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/lot/{lotId}")
    public ResponseEntity<?> getVehiclesByParkingLot(@PathVariable String lotId) {
        try {
            List<Vehicle> vehicles = vehicleService.getVehiclesByParkingLot(lotId);
            return ResponseEntity.ok(vehicles);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
