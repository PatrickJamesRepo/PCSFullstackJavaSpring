package com.example.purrtycatsfullstack.repository;

import com.example.purrtycatsfullstack.model.Asset;
import com.example.purrtycatsfullstack.model.AssetId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, AssetId> {
    // Query method to find assets by collection in the embedded id
    List<Asset> findById_Collection(String collection);
}
