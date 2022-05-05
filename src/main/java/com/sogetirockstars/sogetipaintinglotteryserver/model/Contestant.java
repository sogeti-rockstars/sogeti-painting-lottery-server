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
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

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

    // public void setAddress(Address address) {
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
