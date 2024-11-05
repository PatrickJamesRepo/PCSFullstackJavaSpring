package com.example.purrtycatsfullstack.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class Asset {

    @EmbeddedId
    private AssetId id; // Composite key

    @JsonProperty("name")
    private String name; // The name of the asset

    @JsonProperty("policy_id")
    private String policyId; // The policy ID associated with the asset

    @JsonProperty("amount")
    private String amount; // Amount of the asset

    @Embedded
    @JsonProperty("metadata")
    private Metadata metadata; // Any associated metadata

    // Getters and Setters
    public AssetId getId() {
        return id;
    }

    public void setId(AssetId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
