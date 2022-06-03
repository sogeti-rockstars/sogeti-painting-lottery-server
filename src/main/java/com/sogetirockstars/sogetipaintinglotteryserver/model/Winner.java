package com.sogetirockstars.sogetipaintinglotteryserver.model;

import java.util.ArrayList;
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
public class Winner {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Contestant contestant;

    private Integer placement;

    @ManyToMany(mappedBy = "contestants", cascade = CascadeType.DETACH)
    private List<Lottery> lotteries = new ArrayList<>();

    public Winner() {
    }

    public Winner(Contestant contestant, Integer placement) {
        this.contestant = contestant;
        this.placement = placement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contestant getContestant() {
        return contestant;
    }

    public Long getContestantId() {
        return contestant.getId();
    }

    public void setContestant(Contestant contestant) {
        this.contestant = contestant;
    }

    public Integer getPlacement() {
        return placement;
    }

    public void setPlacement(Integer placement) {
        this.placement = placement;
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
