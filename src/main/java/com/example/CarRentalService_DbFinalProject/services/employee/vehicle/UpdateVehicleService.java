package com.example.CarRentalService_DbFinalProject.services.employee.vehicle;

import com.example.CarRentalService_DbFinalProject.errorHandling.validations.VehicleValidation;
import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class UpdateVehicleService {

    private final DataSource dataSource;

    public UpdateVehicleService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ResponseEntity<String> execute(Vehicle updatedVehicle) {

        int vehicleId = updatedVehicle.getVehicleId();

        String sql = "UPDATE vehicle SET " +
                "make = ?, model = ?, year = ?, license_plate = ?, price_per_day = ?, availability = ?, description = ?, image_url = ? " +
                "WHERE vehicle_id = ?";

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            // Validate the updated vehicle details
            VehicleValidation.execute(updatedVehicle);

            stmt.setString(1, updatedVehicle.getMake());
            stmt.setString(2, updatedVehicle.getModel());
            stmt.setString(3, updatedVehicle.getYear());
            stmt.setString(4, updatedVehicle.getLicensePlate());
            stmt.setDouble(5, updatedVehicle.getPricePerDay());
            stmt.setBoolean(6, updatedVehicle.isAvailability());
            stmt.setString(7, updatedVehicle.getDescription());
            stmt.setString(8, updatedVehicle.getImageUrl());
            stmt.setInt(9, vehicleId);

            int numRowsEffected = stmt.executeUpdate();
            if (numRowsEffected == 0) {
                return ResponseEntity.badRequest().body("Vehicle ID not found");
            }

        } catch (Exception e) {
            System.out.println("Error updating vehicle: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Vehicle update failed");
        }

        return ResponseEntity.ok("Vehicle updated successfully");

    }

}
