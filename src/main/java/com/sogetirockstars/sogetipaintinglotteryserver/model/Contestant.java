package com.sogetirockstars.sogetipaintinglotteryserver.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Contestant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeId;
    private String name;
    private String email;
    private String teleNumber;

    @OneToMany(mappedBy = "contestant", cascade = CascadeType.REMOVE)
    private Set<Winner> winners = new HashSet<>();

    public Contestant() {
    }

    public Contestant(String name, String employeeId, String teleNumber, String email) {
        this.name = name;
        this.email = email;
        this.employeeId = employeeId;
        this.teleNumber = teleNumber;
    }

    public Contestant(String name, String employeeId, String teleNumber, String email, Lottery lottery) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.teleNumber = teleNumber;
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

    public Set<Winner> getWinner() {
        return winners;
    }

    public void setWinner(Set<Winner> winners) {
        this.winners = winners;
    }

    @Override
    public String toString() {
        return "Contestant [" +
              "id="         + ( id         == null ? "null" : id         ) +
            ", email="      + ( email      == null ? "null" : email      ) +
            ", employeeId=" + ( employeeId == null ? "null" : employeeId ) +
            ", name="       + ( name       == null ? "null" : name       ) +
            ", teleNumber=" + ( teleNumber == null ? "null" : teleNumber ) +
            ", winners="    + winners.size() +
            "]";
    }
}
