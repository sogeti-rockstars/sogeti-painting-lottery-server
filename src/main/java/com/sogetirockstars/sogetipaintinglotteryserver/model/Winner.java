package com.sogetirockstars.sogetipaintinglotteryserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Winner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Contestant contestant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Lottery lottery;

    private Integer placement;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private LotteryItem lotteryItem;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public LotteryItem getLotteryItem() {
        return lotteryItem;
    }

    public Long getLotteryItemId() {
        if (this.lotteryItem != null)
            return lotteryItem.getId();
        else
            return null;
    }

    public void setLotteryItem(LotteryItem lotteryItem) {
        this.lotteryItem = lotteryItem;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Lottery getLottery() {
        return lottery;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
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

    @Override
    public String toString() {
        return   "Winner [" +
                        "contestant="  + (contestant == null ? "null" : contestant ) + 
                        ", id="        + (id == null ? "null" :         id         ) +
                        ", lottery="   + (lottery == null ? "null" :    lottery    ) +
                        ", placement=" + (placement == null ? "null" :  placement  ) +
                        "]";
    }
}
