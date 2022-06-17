package com.sogetirockstars.sogetipaintinglotteryserver.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
public class Contestant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeId;
    private String name;
    private String email;
    private String teleNumber;
    private String office;

    @OneToMany(mappedBy = "contestant", cascade = CascadeType.REMOVE)
    private Set<Winner> winners = new HashSet<>();

    public Contestant() {
    }

    public Contestant(String name, String employeeId, String teleNumber, String email, String office) {
        this.name = name;
        this.email = email;
        this.employeeId = employeeId;
        this.teleNumber = teleNumber;
        this.office = office;
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

    public void setOffice(String office) {
        this.office = office;
    }

    public void setWinners(Set<Winner> winners) {
        this.winners = winners;
    }

    public Long getId() {
        return id;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public String getEmployeeId() {
        return employeeId;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public String getName() {
        return name;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public String getEmail() {
        return email;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public String getTeleNumber() {
        return teleNumber;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public String getOffice() {
        return office;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public Set<Winner> getWinners() {
        return winners;
    }

    @Override
    public String toString() {
        return "Contestant [" + "id=" + (id == null ? "null" : id) + ", email=" + (email == null ? "null" : email) + ", employeeId="
                + (employeeId == null ? "null" : employeeId) + ", name=" + (name == null ? "null" : name) + ", teleNumber="
                + (teleNumber == null ? "null" : teleNumber) + ", winners=" + winners.size() + "]";
    }
}
