package com.example.CarRentalService_DbFinalProject.services.employee.vehicle;

import com.example.CarRentalService_DbFinalProject.errorHandling.Exceptions.VehicleException;
import com.example.CarRentalService_DbFinalProject.errorHandling.validations.VehicleValidation;
import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class UpdateVehicleService {

    private final DataSource dataSource;

    public UpdateVehicleService(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void execute(Vehicle v) {

        // validate first (throws VehicleException if invalid)
        VehicleValidation.execute(v);

        // SQL query to update vehicle
        String sql = """
            UPDATE vehicle
               SET make           = ?,
                   model          = ?,
                   year           = ?,
                   license_plate  = ?,
                   price_per_day  = ?,
                   availability   = ?,
                   description    = ?,
                   image_url      = ?
             WHERE vehicle_id     = ?
            """;

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, v.getMake());
            stmt.setString(2, v.getModel());
            stmt.setString(3, v.getYear());
            stmt.setString(4, v.getLicensePlate());
            stmt.setDouble(5, v.getPricePerDay());
            stmt.setBoolean(6, v.isAvailability());
            stmt.setString(7, v.getDescription());
            stmt.setString(8, v.getImageUrl());
            stmt.setInt(9, v.getVehicleId());

            int updated = stmt.executeUpdate();
            if (updated == 0) {
                throw new VehicleException("No vehicle found with ID " + v.getVehicleId());
            }
        } catch (VehicleException vehicleException) {
            throw vehicleException;
        } catch (Exception e) {

            throw new VehicleException("Failed to update vehicle: " + e.getMessage());
        }
    }
}
