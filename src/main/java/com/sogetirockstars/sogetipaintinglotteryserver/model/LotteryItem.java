package com.sogetirockstars.sogetipaintinglotteryserver.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class LotteryItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private String artistName;
    private String size; // Example 12x12cm, string sounds reasonable for now.
    private String frameDescription;
    private String value; // String so we can store currency and formatting for now.
    private String technique;

    // The same lottery item might be added to several lotteries (If it wasnt picked one year.)
    @ManyToMany(mappedBy = "lotteryItems", cascade = CascadeType.DETACH)
    private List<Lottery> lotteries;

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

    public LotteryItem(String pictureUrl, String itemName, String artistName, String size, String frameDescription, String value, String technique,
            Lottery lottery) {
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
