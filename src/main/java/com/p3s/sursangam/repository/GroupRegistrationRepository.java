package com.p3s.sursangam.repository;

import com.p3s.sursangam.model.GroupRegistration;
import com.p3s.sursangam.model.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRegistrationRepository extends JpaRepository<GroupRegistration, Long> {
}
