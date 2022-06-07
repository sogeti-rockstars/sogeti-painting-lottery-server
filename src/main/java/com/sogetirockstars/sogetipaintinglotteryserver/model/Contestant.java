package com.sogetirockstars.sogetipaintinglotteryserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Contestant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeId;
    private String name;
    private String email;
    private String teleNumber;


    @OneToMany(mappedBy = "contestant", cascade = CascadeType.REMOVE)
    private List<Winner> winner = new ArrayList<>();

    public List<Winner> getWinner() {
        return winner;
    }

    public void setWinner(List<Winner> winner) {
        this.winner = winner;
    }

    @ManyToMany
    @JoinTable
    private List<Lottery> lotteries = new ArrayList<>();


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public List<Lottery> getLotteries() {
        return lotteries;
    }

    public List<Long> getLotteries_id() {
        List<Long> lotteryIdList = new ArrayList<>();
        if (!lotteries.isEmpty()) {
            for (Lottery lottery : lotteries) {
                lotteryIdList.add(lottery.getId());
            }
        }
        return lotteryIdList;
    }

    public void setLotteries(List<Lottery> lotteries) {
        this.lotteries = lotteries;
    }


    public Contestant() {
    }

    public Contestant(String name, String address, String employeeId, String teleNumber, String email) {
        this.name = name;
        this.email = email;
        this.employeeId = employeeId;
        this.teleNumber = teleNumber;
    }

    public Contestant(String name, String address, String employeeId, String teleNumber, String email, Lottery lottery) {
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
}
