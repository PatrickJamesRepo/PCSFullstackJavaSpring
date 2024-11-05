package com.example.purrtycatsfullstack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class PageController {

    @GetMapping("/")
    public String home(Model model) {
        // Add any model attributes if needed
        return "index"; // Thymeleaf template name (index.html)
    }

    @GetMapping("/game")
    public String showGame() {
        return "game"; // This will serve the game.html file
    }
    @GetMapping("/minting")
    public String minting(Model model) {
        return "minting"; // Thymeleaf template name (minting.html)
    }

    @GetMapping("/staking")
    public String staking(Model model) {
        return "staking"; // Thymeleaf template name (staking.html)
    }

    @GetMapping("/swap")
    public String swap(Model model) {
        return "swap"; // Thymeleaf template name (swap.html)
    }

    @GetMapping("/vote")
    public String vote(Model model) {
        return "vote"; // Thymeleaf template name (vote.html)
    }

    @GetMapping("/market")
    public String market(Model model) {
        return "market"; // Thymeleaf template name (market.html)
    }

}
