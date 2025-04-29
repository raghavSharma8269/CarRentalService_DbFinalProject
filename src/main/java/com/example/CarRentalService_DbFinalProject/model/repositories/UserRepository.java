package com.example.CarRentalService_DbFinalProject.model.repositories;

import com.example.CarRentalService_DbFinalProject.model.Roles;
import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    // Searches for users via their username
    @Query("SELECT query FROM Users query WHERE query.userName = :userName")
    Optional<Users> findByUserName(@Param("userName") String userName);

    // Searches for users via their email
    @Query("SELECT query FROM Users query WHERE query.email = :email")
    Optional<Users> findByEmail(@Param("email") String email);

    // Retrieves all users, can filter via role, and can search via username, email, and full name
    @Query("SELECT query FROM Users query WHERE " +
            "(:role IS NULL OR query.role = :role) AND " +
            "(:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(query.userName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(query.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(query.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Users> findByRoleAndKeyword(@Param("role") Roles role, @Param("keyword") String keyword);

}




