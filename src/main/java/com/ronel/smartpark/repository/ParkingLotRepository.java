package com.ronel.smartpark.repository;

import com.ronel.smartpark.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, String> {
}