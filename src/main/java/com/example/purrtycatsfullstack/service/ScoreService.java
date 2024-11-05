package com.example.purrtycatsfullstack.service;

import com.example.purrtycatsfullstack.model.Score;
import com.example.purrtycatsfullstack.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;


    public void saveScore(String walletAddress, int points) {
        Score score = new Score();
        score.setWalletAddress(walletAddress);
        score.setPoints(points);
        score.setTimestamp(LocalDateTime.now()); // If you want to log the timestamp

        scoreRepository.save(score); // Save to database
    }

    public void updateScore(String walletAddress, int points) {
        Score score = scoreRepository.findByWalletAddress(walletAddress);
        if (score == null) {
            score = new Score();
            score.setWalletAddress(walletAddress);
            score.setScore(points);
        } else {
            score.setScore(score.getScore() + points);
        }
        scoreRepository.save(score);
    }

    public int getScore(String walletAddress) {
        Score score = scoreRepository.findByWalletAddress(walletAddress);
        return score != null ? score.getScore() : 0;
    }

}
