package com.sogetirockstars.sogetipaintinglotteryserver.model;

import javax.persistence.*;

/**
 * Painting
 */
@Entity
@Table
public class Painting {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String pictureUrl;
    String title;
    String artist;
    String description;

    public Painting() {
    }

    public Painting(String title, String artist, String description) {
        this.artist = artist;
        this.title = title;
        this.description = description;
    }

    public Painting(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return pictureUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
