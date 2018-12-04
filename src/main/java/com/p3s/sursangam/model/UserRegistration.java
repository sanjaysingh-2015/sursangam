package com.p3s.sursangam.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity @Table(name="user_registration") public class UserRegistration{
    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User userId;
    @ManyToOne
    @JoinColumn(name="user_type_id", referencedColumnName = "id")
    private UserType userTypeId;
    @Column(name="registred_on")
    private Date registredOn;
    @Column(name="valid_upto")
    private Date validUpto;
    @Column(name="is_active")
    private Boolean isActive;


    public UserRegistration() {
    }

    public UserRegistration(User userId, UserType userTypeId, Date registredOn, Date validUpto, Boolean isActive) {
        this.userId = userId;
        this.userTypeId = userTypeId;
        this.registredOn = registredOn;
        this.validUpto = validUpto;
        this.isActive = isActive;
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

    public UserType getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(UserType userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Date getRegistredOn() {
        return registredOn;
    }

    public void setRegistredOn(Date registredOn) {
        this.registredOn = registredOn;
    }

    public Date getValidUpto() {
        return validUpto;
    }

    public void setValidUpto(Date validUpto) {
        this.validUpto = validUpto;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "UserRegistration{" +
                "id=" + id +
                ", userId=" + userId +
                ", userTypeId=" + userTypeId +
                ", registredOn=" + registredOn +
                ", validUpto=" + validUpto +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistration that = (UserRegistration) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(userTypeId, that.userTypeId) &&
                Objects.equals(registredOn, that.registredOn) &&
                Objects.equals(validUpto, that.validUpto) &&
                Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, userTypeId, registredOn, validUpto, isActive);
    }
}
