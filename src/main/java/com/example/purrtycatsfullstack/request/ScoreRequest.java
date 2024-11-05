package com.example.purrtycatsfullstack.request;

import com.example.purrtycatsfullstack.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Create a ScoreRequest class to encapsulate the score data
public class ScoreRequest {
    private String walletAddress;
    private int points;

    // Getters and setters
    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}