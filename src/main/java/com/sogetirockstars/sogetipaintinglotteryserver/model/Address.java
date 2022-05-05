package com.sogetirockstars.sogetipaintinglotteryserver.model;

import javax.persistence.*;

@Entity
@Table
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;

    public Address() {
    }

    public Address(String streetName, String streetNumber, String zipCode, String city) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddressStreetName() {
        return streetName;
    }

    public void setAddressStreetName(String addressStreetName) {
        this.streetName = addressStreetName;
    }

    public String getAddressNumber() {
        return streetNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.streetNumber = addressNumber;
    }

    public String getAddressZipCode() {
        return zipCode;
    }

    public void setAddressZipCode(String addressZipCode) {
        this.zipCode = addressZipCode;
    }

    public String getAddressCity() {
        return city;
    }

    public void setAddressCity(String addressCity) {
        this.city = addressCity;
    }
}
