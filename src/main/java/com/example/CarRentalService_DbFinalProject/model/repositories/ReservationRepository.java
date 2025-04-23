package com.example.CarRentalService_DbFinalProject.model.repositories;

import com.example.CarRentalService_DbFinalProject.model.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT r FROM Reservation r WHERE r.user.userId = :userId " +
            "AND (:reservationId IS NULL OR :reservationId = '' OR CAST(r.reservationId AS string) LIKE %:reservationId%)")
    List<Reservation> findAllByUserIdAndReservationIdKeyword(int userId, String reservationId);


}
