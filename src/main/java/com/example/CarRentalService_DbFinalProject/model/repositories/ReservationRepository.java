package com.example.CarRentalService_DbFinalProject.model.repositories;

import com.example.CarRentalService_DbFinalProject.model.entities.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Reservation r WHERE r.vehicleId.vehicleId = :vehicleId")
    void deleteReservationByVehicleId(int vehicleId);


}
