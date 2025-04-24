package com.example.CarRentalService_DbFinalProject.services.employee.reservation;

import com.example.CarRentalService_DbFinalProject.model.entities.Reservation;
import com.example.CarRentalService_DbFinalProject.model.repositories.ReservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GetReservationViaIdService {

    private final ReservationRepository reservationRepository;

    public GetReservationViaIdService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ResponseEntity<Reservation> execute (int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Reservation not found with id: " + reservationId));

        return ResponseEntity.ok(reservation);
    }
}
