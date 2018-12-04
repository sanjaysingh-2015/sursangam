package com.p3s.sursangam.repository;

import com.p3s.sursangam.model.Events;
import com.p3s.sursangam.model.Participant;
import com.p3s.sursangam.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Query("SELECT v.eventId.id FROM Participant v WHERE v.userId.id = :userId")
    Page<Long> findParticipatedEventIdsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT count(distinct v.eventId.id) FROM Participant v WHERE v.userId.id = :userId")
    Long findParticipatedEventIdsByUserId(@Param("userId") Long userId);

}
