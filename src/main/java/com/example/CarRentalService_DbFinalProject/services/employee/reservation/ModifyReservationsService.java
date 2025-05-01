package com.example.CarRentalService_DbFinalProject.services.employee.reservation;

import com.example.CarRentalService_DbFinalProject.model.entities.Reservation;
import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.model.repositories.ReservationRepository;
import com.example.CarRentalService_DbFinalProject.model.repositories.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ModifyReservationsService {
    private final ReservationRepository reservationRepository;
    private final VehicleRepository vehicleRepository;

    public ModifyReservationsService(ReservationRepository reservationRepository, VehicleRepository vehicleRepository) {
        this.reservationRepository = reservationRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public void updateReservation(int reservationId, LocalDateTime start, LocalDateTime end, double totalPrice) {
        // Update the reservation details
        reservationRepository.updateReservation(reservationId, start, end, totalPrice);
    }

    @Transactional
    public void cancelReservation(int reservationId) {
        // Step 1: Fetch the reservation
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        // Step 2: Mark the associated vehicle as available
        Vehicle vehicle = reservation.getVehicleId(); // assuming Reservation has getVehicleId()
        vehicle.setAvailability(true);
        vehicleRepository.save(vehicle);

        // Step 3: Delete the reservation
        reservationRepository.deleteById(reservationId);
    }
}
