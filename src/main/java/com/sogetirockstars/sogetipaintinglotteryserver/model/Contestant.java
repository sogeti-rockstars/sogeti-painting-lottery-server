package com.sogetirockstars.sogetipaintinglotteryserver.model;

import javax.persistence.*;

/**
 * Contestant
 */
@Entity
@Table
public class Contestant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String employeeId;
    private String name;
    private String email;
    private String teleNumber;
    // private String address;              // Todo: take out to it's own object
    // String addressStreetName; // Todo: take out to it's own object
    // String addressNumber;     // Todo: take out to it's own object
    // String addressZipCode;    // Todo: take out to it's own object
    // String addressCity;       // Todo: take out to it's own object

    public Contestant() {
    }

    public Contestant(String name, String address) {
        this.name = name;
        // this.address = address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTeleNumber(String teleNumber) {
        this.teleNumber = teleNumber;
    }

    // public void setAddress(String address) {
    //     this.address = address;
    // }

    public Long getId() {
        return id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTeleNumber() {
        return teleNumber;
    }

    // public String getAddress() {
    //     return address;
    // }
}
