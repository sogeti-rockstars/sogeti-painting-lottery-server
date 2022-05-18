package com.sogetirockstars.sogetipaintinglotteryserver.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Lottery")
@Table(name = "lottery")
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "lottery", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LotteryItem> lotteryItems = new HashSet<>();

    @OneToMany(mappedBy = "lottery", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contestant> contestants = new HashSet<>();

    private Date date;
    private String title;

    @JsonManagedReference
    public Set<Contestant> getContestants() {
        return contestants;
    }

    @JsonManagedReference
    public Set<LotteryItem> getLotteryItems() {
        return lotteryItems;
    }

    public Lottery() {
    }

    public Lottery(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContestants(Set<Contestant> contestants) {
        this.contestants = contestants;
    }

    public void setLotteryItems(Set<LotteryItem> lotteryItems) {
        this.lotteryItems = lotteryItems;
    }

    public void addContestants(Contestant contestant) {
        this.contestants.add(contestant);
    }

    public void addLotteryItems(LotteryItem lotteryItem) {
        this.lotteryItems.add(lotteryItem);
    }
}
