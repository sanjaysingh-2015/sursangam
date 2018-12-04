package com.p3s.sursangam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "event_participation_type")
public class EventParticipationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false, insertable = false, updatable = false)
    private Events eventId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participation_type_id", referencedColumnName = "id", nullable = false)
    private ParticipantType participationTypeId;

//    public EventParticipationType() {
//    }
//
//    public EventParticipationType(Events eventId, ParticipantType participationTypeId) {
//        this.eventId = eventId;
//        this.participationTypeId = participationTypeId;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Events getEventId() {
        return eventId;
    }

    public void setEventId(Events eventId) {
        this.eventId = eventId;
    }

    public ParticipantType getParticipationTypeId() {
        return participationTypeId;
    }

    public void setParticipationTypeId(ParticipantType participationTypeId) {
        this.participationTypeId = participationTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventParticipationType that = (EventParticipationType) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(eventId, that.eventId) &&
                Objects.equals(participationTypeId, that.participationTypeId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, eventId, participationTypeId);
    }
}
