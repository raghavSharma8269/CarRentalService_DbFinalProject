package com.example.CarRentalService_DbFinalProject.errorHandling.validations;

import com.example.CarRentalService_DbFinalProject.model.entities.Reservation;

public class ReservationValidation {

    public static void validate(Reservation reservation) {

        if (reservation.getEnd() == null || reservation.getStart() == null) {
            throw new IllegalArgumentException("Start and end dates cannot be null");
        }

        if (reservation.getStart().isAfter(reservation.getEnd())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        if (reservation.getTotalPrice() <= 0) {
            throw new IllegalArgumentException("Total price must be greater than zero");
        }

        if (reservation.getVehicleId() == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }

    }


}
