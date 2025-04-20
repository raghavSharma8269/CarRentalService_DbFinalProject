package com.example.CarRentalService_DbFinalProject.services.employee.reservation;

import com.example.CarRentalService_DbFinalProject.model.entities.Reservation;
import com.example.CarRentalService_DbFinalProject.model.repositories.ReservationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetAllReservationsService {
    private final ReservationRepository reservationRepository;

    public GetAllReservationsService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ResponseEntity<List<Reservation>> execute(String keyword) {

        List<Reservation> reservations = reservationRepository.searchByKeywordAndPrice(keyword);

        return ResponseEntity.ok(reservations);

    }
}
