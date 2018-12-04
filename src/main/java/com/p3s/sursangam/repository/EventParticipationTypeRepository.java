package com.p3s.sursangam.repository;

import com.p3s.sursangam.model.EventParticipationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;

public interface EventParticipationTypeRepository extends JpaRepository<EventParticipationType, Long> {
    EventParticipationType findByParticipationTypeId(ParameterizedType parameterizedType);
}
