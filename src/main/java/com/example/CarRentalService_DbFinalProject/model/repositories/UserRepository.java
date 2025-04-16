package com.example.CarRentalService_DbFinalProject.model.repositories;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query("SELECT query FROM Users query WHERE query.userName = :userName")
    Optional<Users> findByUserName(@Param("userName") String userName);

    @Query("SELECT query FROM Users query WHERE query.email = :email")
    Optional<Users> findByEmail(@Param("email") String email);
}
