package com.sogetirockstars.sogetipaintinglotteryserver.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "winner")
public class Winner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "contestant_id", nullable = false)
    private Contestant contestant;

    private Integer placement;


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

    @JsonBackReference(value = "winner-contestant")
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

}
