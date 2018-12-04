package com.p3s.sursangam.repository;

import com.p3s.sursangam.model.Events;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository  extends JpaRepository<Events, Long> {
    Optional<Events> findById(Long pollId);

    Page<Events> findByCreatedBy(Long userId, Pageable pageable);

    long countByCreatedBy(Long userId);

    List<Events> findByIdIn(List<Long> pollIds);

    List<Events> findByIdIn(List<Long> pollIds, Sort sort);
}
