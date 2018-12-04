package com.p3s.sursangam.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="continent")
public class Continent {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;

    public Continent() {
    }

    public Continent(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Continent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Continent continent = (Continent) o;
        return Objects.equals(id, continent.id) &&
                Objects.equals(name, continent.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
