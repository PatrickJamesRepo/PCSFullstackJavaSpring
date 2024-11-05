package com.example.purrtycatsfullstack.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;

@Embeddable
public class Metadata {  // Make sure this class is public
    @JsonProperty("image")
    private String imageUrl; // URL of the asset image

    @JsonProperty("description")
    private String description; // Description of the asset

    // Getters and Setters
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
