package com.example.CarRentalService_DbFinalProject.model.repositories;

import com.example.CarRentalService_DbFinalProject.model.entities.Maintenance;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {

    // Delete all maintenance associated with a specific vehicle ID
    @Modifying
    @Transactional
    @Query("DELETE FROM Maintenance r WHERE r.vehicleId.vehicleId = :vehicleId")
    void deleteMaintenanceByVehicleId(int vehicleId);

}
