package com.p3s.sursangam.repository;

import com.p3s.sursangam.model.User;
import com.p3s.sursangam.model.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {
    Optional<UserRegistration> findAllByUserIdAndIsActive(User userId, Boolean active);
}
