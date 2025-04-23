package com.example.CarRentalService_DbFinalProject.services.employee.reservation;

import com.example.CarRentalService_DbFinalProject.model.entities.Reservation;
import com.example.CarRentalService_DbFinalProject.model.repositories.ReservationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetReservationViaIdService {

    private final ReservationRepository reservationRepository;

    public GetReservationViaIdService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ResponseEntity<Reservation> execute (int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        return ResponseEntity.ok(reservation);
    }
}
