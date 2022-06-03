package com.sogetirockstars.sogetipaintinglotteryserver.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Contestant
 */
@Entity
@Table
public class Contestant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonView({JsonViewProfiles.Contestant.class, JsonViewProfiles.Lottery.class})
    private Long id;

    private String employeeId;
    private String name;
    private String email;
    private String teleNumber;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "contestant", cascade = CascadeType.REMOVE)
    private List<Winner> winner = new ArrayList<>();

    public List<Winner> getWinner() {
        return winner;
    }

    public void setWinner(List<Winner> winner) {
        this.winner = winner;
    }

    // Det blir oändliga loopar av två klasser som refererar till varandra
    // och man kan inte lägga @JsonBackReference på Collections.
    // Rätt sätt att göra detta är att göra "JSON view profiles"
    // men det orkar jag inte just nu... Kolla på länken för mer info
    // https://stackoverflow.com/questions/67886252/spring-boot-jpa-infinite-loop-many-to-many
    // @JsonView(JsonViewProfiles.Contestant.class)
    // @ManyToMany(mappedBy = "contestants")
    @ManyToMany
    @JoinTable
    private List<Lottery> lotteries = new ArrayList<>();


    // private List<Long> getWinnerId() {
    // List<Long> ids = new ArrayList<Long>();
    // for (int i = 0; i < winner.size(); i++) {
    // ids.add(winner.get(i).getId());
    // }
    // return ids;
    // }

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

    // public void setAddress(Address address) {
    // this.address = address;
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
    // return address.toString();
    // }

    @JsonBackReference
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
