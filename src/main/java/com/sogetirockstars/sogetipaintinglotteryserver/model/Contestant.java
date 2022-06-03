package com.sogetirockstars.sogetipaintinglotteryserver.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Contestant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeId;
    private String name;
    private String email;
    private String teleNumber;

    @OneToOne
    private Address address;

    @ManyToMany(mappedBy = "contestants", cascade = CascadeType.DETACH) @JsonIgnore
    private List<Lottery> lotteries;

    public Contestant() {
    }

    public Contestant(String name, Address address, String employeeId, String teleNumber, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.employeeId = employeeId;
        this.teleNumber = teleNumber;
    }

    public Contestant(String name, Address address, String employeeId, String teleNumber, String email, Lottery lottery) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.teleNumber = teleNumber;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    // The lotteries field is here to satisfy the @ManyToMany annotation.
    // We return null so we don't get infinite recursion in our JSON generation as @JsonIgnore by itself doesn't solve
    // the problem
    @JsonIgnore
    public List<Lottery> getLotteries() {
        return null;
    }

    public void setLotteries(List<Lottery> lotteries) {
        this.lotteries = lotteries;
    }
}
