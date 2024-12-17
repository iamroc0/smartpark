package com.ronel.smartpark.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ronel.smartpark.model.ParkingLot;
import com.ronel.smartpark.model.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
	List<Vehicle> findByParkedLotIsNotNull();
    List<Vehicle> findByParkedLot(ParkingLot parkingLot);
    List<Vehicle> findByParkedLotLotId(String lotId);
}
