package com.p3s.sursangam.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity @Table(name="group_registration") public class GroupRegistration {
    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User userId;
    @ManyToOne
    @JoinColumn(name="group_type_id", referencedColumnName = "id")
    private GroupType groupTypeId;
    @Column(name="registred_on")
    private Date registredOn;
    @Column(name="valid_upto")
    private Date validUpto;

    public GroupRegistration() {
    }

    public GroupRegistration(User userId, GroupType groupTypeId, Date registredOn, Date validUpto) {
        this.userId = userId;
        this.groupTypeId = groupTypeId;
        this.registredOn = registredOn;
        this.validUpto = validUpto;
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

    public GroupType getGroupTypeId() {
        return groupTypeId;
    }

    public void setGroupTypeId(GroupType groupTypeId) {
        this.groupTypeId = groupTypeId;
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

    @Override
    public String toString() {
        return "GroupRegistration{" +
                "id=" + id +
                ", userId=" + userId +
                ", groupTypeId=" + groupTypeId +
                ", registredOn=" + registredOn +
                ", validUpto=" + validUpto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupRegistration that = (GroupRegistration) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(groupTypeId, that.groupTypeId) &&
                Objects.equals(registredOn, that.registredOn) &&
                Objects.equals(validUpto, that.validUpto);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, groupTypeId, registredOn, validUpto);
    }
}
