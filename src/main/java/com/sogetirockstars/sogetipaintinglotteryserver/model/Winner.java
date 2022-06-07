package com.sogetirockstars.sogetipaintinglotteryserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "winner")
public class Winner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Contestant contestant;

    private Integer placement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Lottery lottery;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Lottery getLottery() {
        return lottery;
    }

    public Long getLottery_id() {
        if (this.lottery != null)
            return lottery.getId();
        else
            return null;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }


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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Contestant getContestant() {
        return contestant;
    }

    public Long getContestantId() {
        if (this.contestant != null)
            return contestant.getId();
        else
            return null;
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

}
