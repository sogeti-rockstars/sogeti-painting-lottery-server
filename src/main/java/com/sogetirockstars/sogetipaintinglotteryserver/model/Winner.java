package com.sogetirockstars.sogetipaintinglotteryserver.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name = "winner")
public class Winner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lottery_id")
    private Lottery lottery;
    @ManyToOne(optional = false)
    @JoinColumn(name = "contestant_id", nullable = false)
    private Contestant contestant;

    private Integer placement;

    @OneToOne(mappedBy = "winner")
    private LotteryItem lotteryItem;


    public Winner() {
    }


    public Winner(Lottery lottery, Contestant contestant, Integer placement, LotteryItem lotteryItem) {
        this.lottery = lottery;
        this.contestant = contestant;
        this.placement = placement;
        this.lotteryItem = lotteryItem;
    }

    public Winner(Lottery lottery, Contestant contestant, Integer placement) {
        this.lottery = lottery;
        this.contestant = contestant;
        this.placement = placement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonBackReference(value = "lottery-winner")
    public Lottery getLottery() {
        return lottery;
    }

    public Long getLotteryId() {
        return lottery.getId();
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
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

    @JsonManagedReference(value = "winner-item")
    public LotteryItem getLotteryItem() {
        return lotteryItem;
    }

    public void setLotteryItem(LotteryItem lotteryItem) {
        this.lotteryItem = lotteryItem;
    }
}
