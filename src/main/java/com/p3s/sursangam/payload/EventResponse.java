package com.p3s.sursangam.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.p3s.sursangam.model.EventParticipationType;
import com.p3s.sursangam.model.Participant;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class EventResponse {
    private Long id;
    private String name;
    private String venue;
    private String city;
    private String latLong;
    private Date eventDate;
    private long duration;
    private String details;

    private List<Participant> participants;
    private GroupSummary createdBy;
    private Instant creationDateTime;
    private List<EventParticipationType> choices;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
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

    public GroupSummary getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(GroupSummary createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public List<EventParticipationType> getChoices() {
        return choices;
    }

    public void setChoices(List<EventParticipationType> choices) {
        this.choices = choices;
    }
}
