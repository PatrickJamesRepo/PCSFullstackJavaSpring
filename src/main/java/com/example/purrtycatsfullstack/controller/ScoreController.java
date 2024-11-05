package com.example.purrtycatsfullstack.controller;

import com.example.purrtycatsfullstack.request.ScoreRequest;
import com.example.purrtycatsfullstack.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    // Update the score for a given wallet address
    @PostMapping("/update")
    public ResponseEntity<String> updateScore(@RequestBody ScoreRequest scoreRequest) {
        scoreService.updateScore(scoreRequest.getWalletAddress(), scoreRequest.getPoints());
        return ResponseEntity.ok("Score updated successfully");
    }


    // Retrieve the score for a given wallet address
    @GetMapping("/get")
    public ResponseEntity<Integer> getScore(@RequestParam String walletAddress) {
        int score = scoreService.getScore(walletAddress);
        return ResponseEntity.ok(score);
    }
}
