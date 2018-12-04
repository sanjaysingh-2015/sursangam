package com.p3s.sursangam.repository;

import com.p3s.sursangam.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    UserType findByName(String name);
}
