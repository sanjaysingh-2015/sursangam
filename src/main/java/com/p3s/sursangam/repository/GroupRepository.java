package com.p3s.sursangam.repository;

import com.p3s.sursangam.model.Groups;
import com.p3s.sursangam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository  extends JpaRepository<Groups, Long> {
    Optional<Groups> findByEmail(String email);

    Optional<Groups> findByUserId(User userId);
}
