package com.p3s.sursangam.payload;

import java.time.Instant;
import java.util.Date;

public class UserProfile implements Profile {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String username;
    private String gender;
    private Date dateOfBirth;
    private String phoneNo;
    private String email;
    private String profileImageUri;
    private String profileImageType;
    private String profileImageSize;
    private Long eventsParticipantCount;
    private Long eventWatchCount;
    private Instant createdAt;

    public UserProfile() {
    }

    public UserProfile(Long id, String firstName, String middleName, String lastName, String username, String gender, Date dateOfBirth, String phoneNo, String email, String profileImageUri, String profileImageType, String profileImageSize, Long eventsParticipantCount, Long eventWatchCount, Instant createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.username = username;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNo = phoneNo;
        this.email = email;
        this.profileImageUri = profileImageUri;
        this.profileImageType = profileImageType;
        this.profileImageSize = profileImageSize;
        this.eventsParticipantCount = eventsParticipantCount;
        this.eventWatchCount = eventWatchCount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUri() {
        return profileImageUri;
    }

    public void setProfileImageUri(String profileImageUri) {
        this.profileImageUri = profileImageUri;
    }

    public String getProfileImageType() {
        return profileImageType;
    }

    public void setProfileImageType(String profileImageType) {
        this.profileImageType = profileImageType;
    }

    public String getProfileImageSize() {
        return profileImageSize;
    }

    public void setProfileImageSize(String profileImageSize) {
        this.profileImageSize = profileImageSize;
    }

    public Long getEventsParticipantCount() {
        return eventsParticipantCount;
    }

    public void setEventsParticipantCount(Long eventsParticipantCount) {
        this.eventsParticipantCount = eventsParticipantCount;
    }

    public Long getEventWatchCount() {
        return eventWatchCount;
    }

    public void setEventWatchCount(Long eventWatchCount) {
        this.eventWatchCount = eventWatchCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
