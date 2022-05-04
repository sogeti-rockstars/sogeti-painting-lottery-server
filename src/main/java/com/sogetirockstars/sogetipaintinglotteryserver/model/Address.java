package com.sogetirockstars.sogetipaintinglotteryserver.model;

import javax.persistence.*;

@Entity
@Table
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String addressStreetName;
    private String addressNumber;
    private String addressZipCode;
    private String addressCity;

    public Address() {
    }

    public Address(String addressStreetName, String addressNumber, String addressZipCode, String addressCity) {
        this.addressStreetName = addressStreetName;
        this.addressNumber = addressNumber;
        this.addressZipCode = addressZipCode;
        this.addressCity = addressCity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    

    public String getAddressStreetName() {
        return addressStreetName;
    }

    public void setAddressStreetName(String addressStreetName) {
        this.addressStreetName = addressStreetName;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getAddressZipCode() {
        return addressZipCode;
    }

    public void setAddressZipCode(String addressZipCode) {
        this.addressZipCode = addressZipCode;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }
}
