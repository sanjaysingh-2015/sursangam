package com.p3s.sursangam.model;

import javax.persistence.*;
import java.util.Objects;

@Entity @Table(name="address") public class Address{
    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @ManyToOne
    @JoinColumn(name="person_id", referencedColumnName = "id")
    private Person personId;
    @Column(name="address_line1")
    private String addressLine1;
    @Column(name="address_line2")
    private String addressLine2;
    @ManyToOne
    @JoinColumn(name="city_id", referencedColumnName = "id")
    private City cityId;
    @ManyToOne
    @JoinColumn(name="state_id", referencedColumnName = "id")
    private State stateId;
    @ManyToOne
    @JoinColumn(name="country_id", referencedColumnName = "id")
    private Country countryId;
    @Column(name="postal_code")
    private String postalCode;


    public Address() {
    }

    public Address(Person personId, String addressLine1, String addressLine2, City cityId, State stateId, Country countryId, String postalCode) {
        this.personId = personId;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.cityId = cityId;
        this.stateId = stateId;
        this.countryId = countryId;
        this.postalCode = postalCode;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public City getCityId() {
        return cityId;
    }

    public void setCityId(City cityId) {
        this.cityId = cityId;
    }

    public State getStateId() {
        return stateId;
    }

    public void setStateId(State stateId) {
        this.stateId = stateId;
    }

    public Country getCountryId() {
        return countryId;
    }

    public void setCountryId(Country countryId) {
        this.countryId = countryId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }


    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", personId=" + personId +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", cityId=" + cityId +
                ", stateId=" + stateId +
                ", countryId=" + countryId +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) &&
                Objects.equals(personId, address.personId) &&
                Objects.equals(addressLine1, address.addressLine1) &&
                Objects.equals(addressLine2, address.addressLine2) &&
                Objects.equals(cityId, address.cityId) &&
                Objects.equals(stateId, address.stateId) &&
                Objects.equals(countryId, address.countryId) &&
                Objects.equals(postalCode, address.postalCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, personId, addressLine1, addressLine2, cityId, stateId, countryId, postalCode);
    }
}
