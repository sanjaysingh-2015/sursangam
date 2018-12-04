package com.p3s.sursangam.model;

import javax.persistence.*;
import java.util.Objects;

@Entity @Table(name="Groups")
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone_no")
    private String phoneNo;
    @Column(name = "email")
    private String email;
    @Column(name="profile_image_uri")
    private String profileImageUri;
    @Column(name="profile_image_type")
    private String profileImageType;
    @Column(name="profile_image_size")
    private String profileImageSize;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User userId;

    public Groups() {
    }

    public Groups(String name, String phoneNo, String email, String profileImageUri, String profileImageType, String profileImageSize, User userId) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.email = email;
        this.profileImageUri = profileImageUri;
        this.profileImageType = profileImageType;
        this.profileImageSize = profileImageSize;
        this.userId = userId;
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "Groups{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", email='" + email + '\'' +
                ", profileImageUri='" + profileImageUri + '\'' +
                ", profileImageType='" + profileImageType + '\'' +
                ", profileImageSize='" + profileImageSize + '\'' +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Groups person = (Groups) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(name, person.name) &&
                Objects.equals(phoneNo, person.phoneNo) &&
                Objects.equals(email, person.email) &&
                Objects.equals(profileImageUri, person.profileImageUri) &&
                Objects.equals(profileImageType, person.profileImageType) &&
                Objects.equals(profileImageSize, person.profileImageSize) &&
                Objects.equals(userId, person.userId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, phoneNo, email, profileImageUri, profileImageType, profileImageSize, userId);
    }
}
