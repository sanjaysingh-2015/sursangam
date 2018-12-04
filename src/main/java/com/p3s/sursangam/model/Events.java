package com.p3s.sursangam.model;

import com.p3s.sursangam.model.audit.UserDateAudit;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "events")
public class Events extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 140)
    @Column(name="name")
    private String name;

    @NotBlank
    @Column(name="venue")
    private String venue;

    @ManyToOne
    @JoinColumn(name="city", referencedColumnName = "id")
    private City city;

    @Column(name="lat_long")
    private String latLong;

    @Column(name="event_date")
    private Date eventDate;

    @Column(name="duration")
    private long duration;

    @Column(name="details")
    private String details;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name="group_id", referencedColumnName = "id")
//    private Groups groupId;

    @OneToMany(
            mappedBy = "eventId",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    private List<Participant> participants = new ArrayList<>();

//    @OneToMany(
//            mappedBy = "eventId",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.EAGER,
//            orphanRemoval = true
//    )
//    @Size(min = 2, max = 6)
//    @Fetch(FetchMode.SELECT)
//    @BatchSize(size = 30)
//    private List<EventParticipationType> choices = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
//
//    public List<EventParticipationType> getChoices() {
//        return choices;
//    }
//
//    public void setChoices(List<EventParticipationType> choices) {
//        this.choices = choices;
//    }
//
    public void addParticipant(Participant participant) {
        participants.add(participant);
        participant.setEventId(this);
    }

    public void removeParticipant(Participant participant) {
        participants.remove(participant);
        participant.setEventId(null);
    }

//    public void addChoice(EventParticipationType choice) {
//        choices.add(choice);
//        choice.setEventId(this);
//    }
//
//    public void removeChoice(EventParticipationType choice) {
//        choices.remove(choice);
//        choice.setEventId(null);
//    }
//
//    public Groups getGroupId() {
//        return groupId;
//    }
//
//    public void setGroupId(Groups groupId) {
//        this.groupId = groupId;
//    }

}
