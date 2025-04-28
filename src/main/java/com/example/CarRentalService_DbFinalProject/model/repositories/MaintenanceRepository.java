package com.example.CarRentalService_DbFinalProject.model.repositories;
import com.example.CarRentalService_DbFinalProject.model.entities.Maintenance;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {

    // Delete all maintenance associated with a specific vehicle ID to avoid deletion anomaly
    @Modifying
    @Transactional
    @Query("DELETE FROM Maintenance r WHERE r.vehicleId.vehicleId = :vehicleId")
    void deleteMaintenanceByVehicleId(int vehicleId);

    // Search maintenance by keyword via vehicle(make, model, license plate, year)
    @Query("SELECT query FROM Maintenance query WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(query.vehicleId.make) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(query.vehicleId.model) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(query.vehicleId.licensePlate) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(query.vehicleId.year) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Maintenance> searchByKeywordAndPrice(String keyword);

}
