package com.sogetirockstars.sogetipaintinglotteryserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Painting
 */
@Entity
@Table
public class LotteryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Internal object id;

    private String itemName;
    private String artistName;
    private String size; // Example 12x12cm, string sounds reasonable for now.
    private String frameDescription;
    private String value; // String so we can store currency and formatting for now.
    private String technique;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Lottery lottery;

    @OneToOne(mappedBy = "lotteryItem")
    private Winner winner;

    public LotteryItem() {
    }

    public LotteryItem(String itemName, String artistName) {
        this.artistName = artistName;
        this.itemName = itemName;
    }

    public LotteryItem(String pictureUrl, String itemName, String artistName, String size, String frameDescription, String value, String technique) {
        this.itemName = itemName;
        this.artistName = artistName;
        this.size = size;
        this.frameDescription = frameDescription;
        this.value = value;
        this.technique = technique;
    }

    public Long getId() {
        return id;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Lottery getLottery() {
        return lottery;
    }

    public Long getLotteryId() {
        if (lottery != null)
            return lottery.getId();
        else
            return null;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    public String getItemName() {
        return itemName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getSize() {
        return size;
    }

    public String getFrameDescription() {
        return frameDescription;
    }

    public String getValue() {
        return value;
    }

    public String getTechnique() {
        return technique;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setFrameDescription(String frameDescription) {
        this.frameDescription = frameDescription;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Winner getWinner() {
        return winner;
    }

    public Long getWinnerId() {
        if (winner != null)
            return winner.getId();
        else
            return null;
    }

    public void setWinner(Winner winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "LotteryItem [" + "artistName=" + (artistName == null ? "null" : artistName) + ", frameDescription="
                + (frameDescription == null ? "null" : frameDescription) + ", id=" + (id == null ? "null" : id) + ", itemName="
                + (itemName == null ? "null" : itemName) + ", lottery=" + (lottery == null ? "null" : lottery) + ", size=" + (size == null ? "null" : size)
                + ", technique=" + (technique == null ? "null" : technique) + ", value=" + (value == null ? "null" : value) + ", winnerid="
                + (winner == null ? "null" : winner.getId()) + "]";
    }
}
