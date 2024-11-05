package com.example.purrtycatsfullstack.service;

import com.example.purrtycatsfullstack.model.Asset; // Assuming you have an Asset model
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WalletService {

    @Autowired
    private RestTemplate restTemplate;

    private String selectedWallet;
    private String connectedWalletAddress; // Store the wallet address
    private double totalAdaAmount;

    public String connectWallet(String walletType) {
        // Logic to connect the wallet (this is usually wallet-specific)
        // Example: Simulating the fetching of a wallet address
        connectedWalletAddress = simulateWalletConnection(walletType); // Mock function

        if (connectedWalletAddress == null) {
            return "Failed to connect wallet: Invalid wallet type or connection issue.";
        }

        totalAdaAmount = fetchWalletBalance(walletType); // Fetch the balance
        return "Wallet connected successfully!";
    }

    public double fetchWalletBalance(String walletType) {
        // Simulate fetching balance (replace with actual logic)
        // In a real application, you would call an API to get the wallet balance
        return Math.random() * 1000; // Random balance for demonstration
    }

    public List<Asset> fetchAssetsForStakeAddress(String stakeAddress) {
        // Make a call to the Blockfrost API to get assets for the connected wallet
        String apiUrl = String.format("https://cardano-mainnet.blockfrost.io/api/v0/assets/addresses/%s", connectedWalletAddress);

        try {
            ResponseEntity<List<Asset>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Asset>>() {}
            );
            return response.getBody(); // Return the list of assets
        } catch (Exception e) {
            System.err.println("Error fetching assets: " + e.getMessage());
            return List.of(); // Return an empty list in case of an error
        }
    }

    private String simulateWalletConnection(String walletType) {
        // Mock wallet addresses for demonstration purposes
        switch (walletType) {
            case "nami":
                return "nami_wallet_address_example";
            case "eternl":
                return "eternl_wallet_address_example";
            case "lace":
                return "lace_wallet_address_example";
            case "yoroi":
                return "yoroi_wallet_address_example";
            default:
                return null; // Invalid wallet type
        }
    }

    public String getSelectedWallet() {
        return selectedWallet;
    }

    public double getTotalAdaAmount() {
        return totalAdaAmount;
    }

    public String getConnectedWalletAddress() {
        return connectedWalletAddress;
    }

    public boolean isWalletConnected() {
        return connectedWalletAddress != null;
    }
}
