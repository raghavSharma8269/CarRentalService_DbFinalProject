package com.example.CarRentalService_DbFinalProject.model.repositories;

import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {


    // Custom query to search for available vehicles by keyword and price range
    @Query("SELECT v FROM Vehicle v WHERE " +
            "v.availability = true AND " +
            "(:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(v.make) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(v.model) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(v.year AS string) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(v.licensePlate) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:minPrice IS NULL OR v.pricePerDay >= :minPrice) AND " +
            "(:maxPrice IS NULL OR v.pricePerDay <= :maxPrice)")
    List<Vehicle> searchAvailableByKeywordAndPrice(String keyword, Double minPrice, Double maxPrice);



    // Search vehicles by keyword via make, model, year, license_plate, min price - max price
    @Query("SELECT query FROM Vehicle query WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(query.make) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(query.model) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(query.year) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(query.licensePlate) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:minPrice IS NULL OR query.pricePerDay >= :minPrice) AND " +
            "(:maxPrice IS NULL OR query.pricePerDay <= :maxPrice)")
    List<Vehicle> searchByKeywordAndPrice(String keyword, Double minPrice, Double maxPrice);



}
