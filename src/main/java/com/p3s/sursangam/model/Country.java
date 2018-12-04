package com.p3s.sursangam.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="country")
public class Country {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @ManyToOne
    @JoinColumn(name="continent_id", referencedColumnName = "id")
    private Continent continentId;

    public Country() {
    }

    public Country(String name, Continent continentId) {
        this.name = name;
        this.continentId = continentId;
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

    public Continent getContinentId() {
        return continentId;
    }

    public void setContinentId(Continent continentId) {
        this.continentId = continentId;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", continentId=" + continentId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id) &&
                Objects.equals(name, country.name) &&
                Objects.equals(continentId, country.continentId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, continentId);
    }
}
