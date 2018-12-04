package com.p3s.sursangam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.p3s.sursangam.model.audit.UserDateAudit;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "participant")
public class Participant extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User userId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="event_id", referencedColumnName = "id")
    private Events eventId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="participant_type_id", referencedColumnName = "id")
    private ParticipantType participantTypeId;

    public Participant() {
    }

    public Participant(User userId, Events eventId, ParticipantType participantTypeId) {
        this.userId = userId;
        this.eventId = eventId;
        this.participantTypeId = participantTypeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Events getEventId() {
        return eventId;
    }

    public void setEventId(Events eventId) {
        this.eventId = eventId;
    }

    public ParticipantType getParticipantTypeId() {
        return participantTypeId;
    }

    public void setParticipantTypeId(ParticipantType participantTypeId) {
        this.participantTypeId = participantTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(eventId, that.eventId) &&
                Objects.equals(participantTypeId, that.participantTypeId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, eventId, participantTypeId);
    }
}
