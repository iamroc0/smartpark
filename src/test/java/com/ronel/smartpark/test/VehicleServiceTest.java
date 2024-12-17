package com.ronel.smartpark.test;

import com.ronel.smartpark.model.Vehicle;
import com.ronel.smartpark.repository.VehicleRepository;
import com.ronel.smartpark.service.VehicleService;
import com.ronel.smartpark.dto.VehicleRegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterVehicle_Success() {
        VehicleRegistrationRequest request = new VehicleRegistrationRequest();
        request.setLicensePlate("ABC-123");
        request.setType("Car");
        request.setOwnerName("John Doe");

        Vehicle savedVehicle = new Vehicle();
        savedVehicle.setLicensePlate("ABC-123");
        savedVehicle.setType("Car");
        savedVehicle.setOwnerName("John Doe");

        when(vehicleRepository.existsById("ABC-123")).thenReturn(false);
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(savedVehicle);


        Vehicle result = vehicleService.registerVehicle(request);


        assertNotNull(result);
        assertEquals("ABC-123", result.getLicensePlate());
        assertEquals("Car", result.getType());
        assertEquals("John Doe", result.getOwnerName());

        verify(vehicleRepository, times(1)).existsById("ABC-123");
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    public void testRegisterVehicle_VehicleAlreadyExists() {

        VehicleRegistrationRequest request = new VehicleRegistrationRequest();
        request.setLicensePlate("ABC-123");
        request.setType("Car");
        request.setOwnerName("John Doe");

        when(vehicleRepository.existsById("ABC-123")).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            vehicleService.registerVehicle(request);
        });

        assertEquals("Vehicle already registered.", exception.getMessage());

        verify(vehicleRepository, times(1)).existsById("ABC-123");
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    public void testRegisterVehicle_InvalidLicensePlate() {

        VehicleRegistrationRequest request = new VehicleRegistrationRequest();
        request.setLicensePlate("INVALID@123");
        request.setType("Car");
        request.setOwnerName("John Doe");


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            vehicleService.registerVehicle(request);
        });

        assertEquals("Invalid license plate format.", exception.getMessage());

        verify(vehicleRepository, never()).existsById(anyString());
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    public void testRegisterVehicle_InvalidOwnerName() {

        VehicleRegistrationRequest request = new VehicleRegistrationRequest();
        request.setLicensePlate("ABC-123");
        request.setType("Car");
        request.setOwnerName("John@Doe");


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            vehicleService.registerVehicle(request);
        });

        assertEquals("Owner name must contain only letters and spaces.", exception.getMessage());

        verify(vehicleRepository, never()).existsById(anyString());
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }
}
