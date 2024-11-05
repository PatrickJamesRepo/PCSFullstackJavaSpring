package com.example.purrtycatsfullstack.controller;

import com.example.purrtycatsfullstack.model.Asset; // Assuming you have an Asset model
import com.example.purrtycatsfullstack.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WalletController {

    @Autowired
    private WalletService walletService;

    private Map<String, Double> walletBalances = new HashMap<>();
    private String selectedWallet;
    private String connectedWalletLogo;
    private double totalAdaAmount;

    public WalletController() {
        walletBalances.put("nami", 0.0);
        walletBalances.put("eternl", 0.0);
        walletBalances.put("lace", 0.0);
        walletBalances.put("yoroi", 0.0);
    }

    @GetMapping("/wallet")
    public String showWallet(Model model) {
        model.addAttribute("selectedWallet", selectedWallet);
        model.addAttribute("connectedWalletLogo", connectedWalletLogo);
        model.addAttribute("totalAdaAmount", totalAdaAmount);
        model.addAttribute("walletBalances", walletBalances);
        return "navbar";
    }

    @PostMapping("/wallet/connect")
    public String connectWallet(@RequestParam String walletType, Model model) {
        String message = walletService.connectWallet(walletType);

        // Add the message to the model
        model.addAttribute("toastMessage", message);
        model.addAttribute("toastType", message.contains("failed") ? "error" : "success");

        // Check if the wallet was connected successfully
        if (walletService.isWalletConnected()) {
            totalAdaAmount = walletService.getTotalAdaAmount();
            model.addAttribute("totalAdaAmount", totalAdaAmount);
            // Fetch and add assets for display
            List<Asset> assets = walletService.fetchAssetsForStakeAddress(walletService.getConnectedWalletAddress());
            model.addAttribute("assets", assets);
        } else {
            model.addAttribute("totalAdaAmount", 0.0); // Reset balance if not connected
        }

        return "redirect:/wallet"; // Redirect to the wallet page to reflect changes
    }

    @PostMapping("/wallet/disconnect")
    public String disconnectWallet(Model model) {
        // Reset the connection state
        selectedWallet = null;
        connectedWalletLogo = null;
        totalAdaAmount = 0;
        model.addAttribute("toastMessage", "Wallet disconnected successfully!");
        model.addAttribute("toastType", "success");
        updateModelAttributes(model);
        return "redirect:/wallet";
    }

    private void updateModelAttributes(Model model) {
        model.addAttribute("selectedWallet", selectedWallet);
        model.addAttribute("connectedWalletLogo", connectedWalletLogo);
        model.addAttribute("totalAdaAmount", walletService.getTotalAdaAmount());
        model.addAttribute("walletBalances", walletBalances);
    }
}
