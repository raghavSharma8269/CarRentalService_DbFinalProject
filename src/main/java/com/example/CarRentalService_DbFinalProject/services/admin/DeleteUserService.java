package com.example.CarRentalService_DbFinalProject.services.admin;

import com.example.CarRentalService_DbFinalProject.model.repositories.ReservationRepository;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public DeleteUserService(UserRepository userRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    public ResponseEntity<String> execute(int userId) {

        reservationRepository.deleteReservationByUserId(userId);
        userRepository.deleteById(userId);
        return ResponseEntity.ok("Deleted user ");
    }

}
