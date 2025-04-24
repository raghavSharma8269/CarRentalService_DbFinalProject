package com.example.CarRentalService_DbFinalProject.services.customer;

import com.example.CarRentalService_DbFinalProject.model.entities.Reservation;
import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.repositories.ReservationRepository;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import com.example.CarRentalService_DbFinalProject.security.AuthUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetReservationsService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final AuthUtil authUtil;

    public GetReservationsService(
            ReservationRepository reservationRepository,
            UserRepository userRepository,
            AuthUtil authUtil
    ) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.authUtil = authUtil;
    }

    public ResponseEntity<List<Reservation>> execute(String reservationId) {

        String username = authUtil.getLoggedInUsername();

        Optional<Users> optionalUser = userRepository.findByUserName(username);

        Users user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));

        List<Reservation> reservations = reservationRepository.findAllByUserIdAndReservationIdKeyword(user.getUserId(), reservationId);

        return ResponseEntity.ok(reservations);

    }

}
