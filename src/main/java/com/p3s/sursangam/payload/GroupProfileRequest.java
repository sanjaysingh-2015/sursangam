package com.p3s.sursangam.payload;

import java.util.Date;

public class GroupProfileRequest {
    private String name;
    private String phoneNo;
    private String email;
    private String profileImageUri;
    private String profileImageType;
    private String profileImageSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
