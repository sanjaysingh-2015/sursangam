package com.p3s.sursangam.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.p3s.sursangam.util.CustomerDateAndTimeDeserialize;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity @Table(name="person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "gender")
    private String gender;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
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

    public Person() {
    }

    public Person(String firstName, String middleName, String lastName, String gender, Date dateOfBirth, String phoneNo, String email, String profileImageUri, String profileImageType, String profileImageSize, User userId) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
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
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
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
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(middleName, person.middleName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(gender, person.gender) &&
                Objects.equals(dateOfBirth, person.dateOfBirth) &&
                Objects.equals(phoneNo, person.phoneNo) &&
                Objects.equals(email, person.email) &&
                Objects.equals(profileImageUri, person.profileImageUri) &&
                Objects.equals(profileImageType, person.profileImageType) &&
                Objects.equals(profileImageSize, person.profileImageSize) &&
                Objects.equals(userId, person.userId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, middleName, lastName, gender, dateOfBirth, phoneNo, email, profileImageUri, profileImageType, profileImageSize, userId);
    }
}
