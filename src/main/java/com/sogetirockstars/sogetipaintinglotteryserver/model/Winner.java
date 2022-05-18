package com.sogetirockstars.sogetipaintinglotteryserver.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contestant_id")
    private Contestant contestant;

    private Integer placement;
    @OneToOne
    private LotteryItem lotteryItem;

    public Winner() {
    }


    public Winner(Lottery lottery, Contestant contestant, int placement, LotteryItem lotteryItem) {
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

    @JsonBackReference
    public Lottery getLottery() {
        return lottery;
    }

    public Long getLotteryId() {
        return lottery.getId();
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    @JsonBackReference
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

    public LotteryItem getLotteryItem() {
        return lotteryItem;
    }

    public void setLotteryItem(LotteryItem lotteryItem) {
        this.lotteryItem = lotteryItem;
    }
}
