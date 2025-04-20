package com.example.CarRentalService_DbFinalProject.model.repositories;

import com.example.CarRentalService_DbFinalProject.model.entities.Reservation;
import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {


    // Delete all reservations associated with a specific vehicle ID
    @Modifying
    @Transactional
    @Query("DELETE FROM Reservation r WHERE r.vehicleId.vehicleId = :vehicleId")
    void deleteReservationByVehicleId(int vehicleId);

    // Search reservations by keyword via full name, email, username, licence plate
    @Query("SELECT query FROM Reservation query WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(query.user.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(query.user.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(query.user.userName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(query.vehicleId.licensePlate) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Reservation> searchByKeywordAndPrice(String keyword);
}
