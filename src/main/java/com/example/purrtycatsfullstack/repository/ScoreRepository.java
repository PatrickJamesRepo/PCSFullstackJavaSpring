package com.example.purrtycatsfullstack.repository;

import com.example.purrtycatsfullstack.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    Score findByWalletAddress(String walletAddress);
}
