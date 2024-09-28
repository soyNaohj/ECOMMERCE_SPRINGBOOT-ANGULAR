package com.Ecommerce.ecom.repository;

import com.Ecommerce.ecom.entity.User;
import com.Ecommerce.ecom.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { //Extiende interfaz que proporciona operaciones CRUD comunes para los repositorios JPA.
    Optional<User> findFirstByEmail(String email);
    User findByRole(UserRole userRole);
}

