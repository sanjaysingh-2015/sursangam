package com.p3s.sursangam.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="city")
public class City {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @ManyToOne
    @JoinColumn(name="state_id", referencedColumnName = "id")
    private State stateId;

    public City() {
    }

    public City(String name, State stateId) {
        this.name = name;
        this.stateId = stateId;
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

    public State getStateId() {
        return stateId;
    }

    public void setStateId(State stateId) {
        this.stateId = stateId;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stateId=" + stateId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(id, city.id) &&
                Objects.equals(name, city.name) &&
                Objects.equals(stateId, city.stateId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, stateId);
    }
}
