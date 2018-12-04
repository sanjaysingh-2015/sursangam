package com.p3s.sursangam.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="group_type") public class GroupType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;
    @Column(name="code")
    private String code;
    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name="name")
    private GroupTypeName name;
    @Column(name="duration")
    private Integer duration;

    public GroupType() {
    }

    public GroupType(String code, GroupTypeName name, Integer duration) {
        this.code = code;
        this.name = name;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public GroupTypeName getName() {
        return name;
    }

    public void setName(GroupTypeName name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "GroupType{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupType userType = (GroupType) o;
        return Objects.equals(id, userType.id) &&
                Objects.equals(code, userType.code) &&
                Objects.equals(name, userType.name) &&
                Objects.equals(duration, userType.duration) ;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, code, name, duration);
    }
}
