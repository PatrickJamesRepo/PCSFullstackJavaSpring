package com.example.purrtycatsfullstack.walletmanager;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WalletManager {
    private Map<String, Double> walletBalances;
    private String selectedWallet;
    private String connectedWalletLogo;
    private double totalAdaAmount;

    public WalletManager() {
        this.walletBalances = new HashMap<>();
        this.selectedWallet = null;
        this.connectedWalletLogo = null;
        this.totalAdaAmount = 0.0;
    }

    public void connectWallet(String walletType) {
        try {
            if (!walletBalances.containsKey(walletType)) {
                throw new Exception(walletType + " wallet not available");
            }

            // Simulate wallet connection
            System.out.println("Connecting to wallet: " + walletType);
            displayToast(walletType + " wallet connected successfully.");
            selectedWallet = walletType;
            totalAdaAmount = fetchWalletBalance(walletType);
            connectedWalletLogo = getWalletLogo(walletType);
        } catch (Exception e) {
            System.err.println("Error connecting wallet: " + e.getMessage());
            displayToast(walletType + " wallet failed to connect", "error");
        }
    }

    public void disconnectWallet() {
        selectedWallet = null;
        connectedWalletLogo = null;
        totalAdaAmount = 0.0;
        displayToast("Wallet disconnected successfully.");
    }

    public double fetchWalletBalance(String walletType) {
        // Simulate fetching wallet balance
        return new Random().nextDouble() * 1000; // Random balance for demo
    }

    private String getWalletLogo(String walletType) {
        switch (walletType) {
            case "nami":
                return "path/to/namiImage.png";
            case "eternl":
                return "path/to/eternlImage.png";
            case "lace":
                return "path/to/laceImage.png";
            case "yoroi":
                return "path/to/yoroiImage.png";
            default:
                return null;
        }
    }

    private void displayToast(String message) {
        System.out.println("Toast: " + message);
    }

    private void displayToast(String message, String type) {
        System.out.println(type + " Toast: " + message);
    }

    // Getters for selectedWallet, totalAdaAmount, and connectedWalletLogo
    public String getSelectedWallet() {
        return selectedWallet;
    }

    public double getTotalAdaAmount() {
        return totalAdaAmount;
    }

    public String getConnectedWalletLogo() {
        return connectedWalletLogo;
    }
}
