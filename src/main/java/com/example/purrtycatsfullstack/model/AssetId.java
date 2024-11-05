package com.example.purrtycatsfullstack.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class AssetId implements Serializable {
    private String collection; // Represents part of the composite key
    private String assetId; // Represents part of the composite key

    // Default constructor
    public AssetId() {}

    // Parameterized constructor
    public AssetId(String collection, String assetId) {
        this.collection = collection;
        this.assetId = assetId;
    }

    // Getters and Setters
    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    // Override equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssetId)) return false;
        AssetId assetId = (AssetId) o;
        return Objects.equals(collection, assetId.collection) &&
                Objects.equals(this.assetId, assetId.assetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collection, assetId);
    }
}
