package com.example.purrtycatsfullstack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String walletAddress;
    private int score;
    private String playerId;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getRewardsAddress() {
        return rewardsAddress;
    }

    public void setRewardsAddress(String rewardsAddress) {
        this.rewardsAddress = rewardsAddress;
    }

    private String rewardsAddress;
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {

    }

    public void setTimestamp(LocalDateTime now) {
    }

    public void setPoints(int points) {
    }

}
