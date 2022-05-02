package com.sogetirockstars.sogetipaintinglotteryserver.model;

import javax.persistence.*;

/**
 * Painting
 */
@Entity
@Table
public class LotteryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; // Internal object id;
    private Integer lotteryId; // Visible to users

    private String pictureUrl;
    private String itemName;
    private String artistName;
    private String size; // Example 12x12cm, string sounds reasonable for now.
    private String frameDescription;
    private String value; // String so we can store currency and formatting for now.
    private String technique;

    public LotteryItem() {
    }

    public LotteryItem(String itemName, String artistName) {
        this.artistName = artistName;
        this.itemName = itemName;
    }

    public LotteryItem(int lotteryId, String pictureUrl, String itemName, String artistName,
                       String size, String frameDescription, String value, String technique) {
        this.lotteryId = lotteryId;
        this.pictureUrl = pictureUrl;
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

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getPictureUrl() {
        return pictureUrl;
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

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
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
}
