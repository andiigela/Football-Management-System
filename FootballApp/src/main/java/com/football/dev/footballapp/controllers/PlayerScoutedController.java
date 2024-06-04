package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.models.PlayerScouted;
import com.football.dev.footballapp.services.PlayerScoutedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/playerscouted")
public class PlayerScoutedController {

    @Autowired
    private PlayerScoutedService playerScoutedService;

    @GetMapping
    public String listPlayersScouted(Model model) {
        List<PlayerScouted> playersScouted = playerScoutedService.findAll();
        model.addAttribute("playersScouted", playersScouted);
        return "playerscouted/list";
    }

    @GetMapping("/{id}")
    public String getPlayerScouted(@PathVariable Long id, Model model) {
        PlayerScouted playerScouted = playerScoutedService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid player ID:" + id));
        model.addAttribute("playerScouted", playerScouted);
        return "playerscouted/detail";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("playerScouted", new PlayerScouted());
        return "playerscouted/add";
    }

    @PostMapping("/add")
    public String addPlayerScouted(@ModelAttribute PlayerScouted playerScouted) {
        playerScoutedService.save(playerScouted);
        return "redirect:/playerscouted";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        PlayerScouted playerScouted = playerScoutedService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid player ID:" + id));
        model.addAttribute("playerScouted", playerScouted);
        return "playerscouted/edit";
    }

    @PostMapping("/edit/{id}")
    public String editPlayerScouted(@PathVariable Long id, @ModelAttribute PlayerScouted playerScouted) {
        playerScoutedService.save(playerScouted);
        return "redirect:/playerscouted";
    }

    @GetMapping("/delete/{id}")
    public String deletePlayerScouted(@PathVariable Long id) {
        playerScoutedService.deleteById(id);
        return "redirect:/playerscouted";
    }
}
