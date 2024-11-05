package com.example.purrtycatsfullstack.controller;

import com.example.purrtycatsfullstack.model.Asset;
import com.example.purrtycatsfullstack.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AssetController {

    private final AssetService assetService;

    @Value("${blockfrost.project_id}")
    private String blockfrostProjectId;

    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("/assets")
    public String getAssets(@RequestParam String collection,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        // Fetch the policy ID based on the collection
        String policyId = assetService.getPolicyIdByCollection(collection);

        if (policyId.isEmpty()) {
            model.addAttribute("error", "Invalid collection specified.");
            return "index"; // Return to the index page with an error
        }

        List<Asset> assets = assetService.fetchAssets(policyId, page, size);
        model.addAttribute("assets", assets);
        return "index"; // Ensure this matches your Thymeleaf template name
    }
}
