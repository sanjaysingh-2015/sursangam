package com.p3s.sursangam.repository;

import com.p3s.sursangam.model.ParticipantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantTypeRepository extends JpaRepository<ParticipantType, Long> {
}
