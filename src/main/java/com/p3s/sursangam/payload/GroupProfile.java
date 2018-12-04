package com.p3s.sursangam.payload;

import java.time.Instant;
import java.util.Date;

public class GroupProfile implements Profile {
    private Long id;
    private String name;
    private String username;
    private String phoneNo;
    private String email;
    private String profileImageUri;
    private String profileImageType;
    private String profileImageSize;
    private Long eventsCount;
    private Instant createdAt;

    public GroupProfile() {
    }

    public GroupProfile(Long id, String name, String username, String phoneNo, String email, String profileImageUri, String profileImageType, String profileImageSize, Long eventsCount, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.phoneNo = phoneNo;
        this.email = email;
        this.profileImageUri = profileImageUri;
        this.profileImageType = profileImageType;
        this.profileImageSize = profileImageSize;
        this.eventsCount = eventsCount;
        this.createdAt = createdAt;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Long getEventsCount() {
        return eventsCount;
    }

    public void setEventsCount(Long eventsCount) {
        this.eventsCount = eventsCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
