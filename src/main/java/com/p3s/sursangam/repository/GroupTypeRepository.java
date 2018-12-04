package com.p3s.sursangam.repository;

import com.p3s.sursangam.model.GroupType;
import com.p3s.sursangam.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupTypeRepository extends JpaRepository<GroupType, Long> {
    GroupType findByName(String name);
}
