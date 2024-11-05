package com.example.purrtycatsfullstack.service;

import com.example.purrtycatsfullstack.model.Asset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AssetService {

    @Value("${blockfrost.project_id}")
    private String blockfrostProjectId;

    private final RestTemplate restTemplate;

    public AssetService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Asset> fetchAssets(String policyId, int page, int size) {
        String url = String.format("https://cardano-mainnet.blockfrost.io/api/v0/assets/policy/%s?page=%d&count=%d", policyId, page, size);
        HttpHeaders headers = new HttpHeaders();
        headers.set("project_id", blockfrostProjectId);

        // Create the HttpEntity with headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<Asset>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Asset>>() {});
            return response.getBody();
        } catch (RestClientException e) {
            System.err.println("Error fetching assets: " + e.getMessage());
            return List.of(); // Return an empty list in case of error
        }
    }

    public Asset fetchAssetDetails(String assetId) {
        String url = String.format("https://cardano-mainnet.blockfrost.io/api/v0/assets/%s", assetId);
        HttpHeaders headers = new HttpHeaders();
        headers.set("project_id", blockfrostProjectId);

        // Create the HttpEntity with headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Asset> response = restTemplate.exchange(url, HttpMethod.GET, entity, Asset.class);
            return response.getBody();
        } catch (RestClientException e) {
            System.err.println("Error fetching asset details: " + e.getMessage());
            return null; // Return null in case of error
        }
    }

    public String getPolicyIdByCollection(String collection) {
        switch (collection) {
            case "Original Collection":
                return "f96584c4fcd13cd1702c9be683400072dd1aac853431c99037a3ab1e";
            case "Halloween Collection":
                return "52f53a3eb07121fcbec36dae79f76abedc6f3a877f8c8857e6c204d1";
            case "PCS/YUMMI Collection":
                return "d91b5642303693f5e7a188748bfd1a26c925a1c5e382e19a13dd263c";
            default:
                return ""; // Return an empty string for invalid collections
        }
    }
}
