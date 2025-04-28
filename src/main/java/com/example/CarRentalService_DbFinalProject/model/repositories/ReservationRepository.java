package com.example.CarRentalService_DbFinalProject.model.repositories;

import com.example.CarRentalService_DbFinalProject.model.entities.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    // Retrieve all reservations associated with a specific user ID, allows for searching by reservation ID
    @Query("SELECT r FROM Reservation r WHERE r.user.userId = :userId " +
            "AND (:reservationId IS NULL OR :reservationId = '' OR CAST(r.reservationId AS string) LIKE %:reservationId%)")
    List<Reservation> findAllByUserIdAndReservationIdKeyword(int userId, String reservationId);


    // Deletes all reservations associated with a specific user ID to avoid deletion anomaly
    @Modifying
    @Transactional
    @Query("DELETE FROM Reservation query WHERE query.user.userId = :userId")
    void deleteReservationByUserId(@Param("userId") int userId);

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
