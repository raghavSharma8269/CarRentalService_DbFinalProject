package com.example.CarRentalService_DbFinalProject.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id", updatable = false, nullable = false)
    private int vehicleId;

    @Column(name = "make", nullable = false)
    private String make;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "year", nullable = false)
    private String year;

    @Column(name = "license_plate", nullable = false)
    private String licensePlate;

    @Column(name = "price_per_day", nullable = false)
    private double pricePerDay;

    @Column(name = "availability", nullable = false)
    private boolean availability;

    @Column(name = "description", nullable = false)
    private boolean description;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;


}
