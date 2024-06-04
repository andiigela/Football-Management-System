package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.dto.PlayerScoutedDto;
import com.football.dev.footballapp.models.PlayerScoutingReports;
import com.football.dev.footballapp.services.PlayerScoutingReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

import java.util.List;

@Controller
@RequestMapping("/playerscoutingreports")
public class PlayerScoutingReportController {

    @Autowired
    private PlayerScoutingReportService playerScoutingReportService;

    @GetMapping
    public String listPlayerScoutingReports(Model model) {
        List<PlayerScoutedDto> reports = playerScoutingReportService.findAll().stream()
                .map(playerScoutingReportService::convertToDto)
                .collect(Collectors.toList());
        model.addAttribute("reports", reports);
        return "redirect:/"; // Redirect to home page or another appropriate page
    }

    @GetMapping("/{id}")
    public String getPlayerScoutingReports(@PathVariable Long id, Model model) {
        PlayerScoutingReports report = playerScoutingReportService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid report ID: " + id));
        model.addAttribute("report", playerScoutingReportService.convertToDto(report));
        return "playerscoutingreports/detail";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("report", new PlayerScoutedDto());
        return "playerscoutingreports/add";
    }

    @PostMapping("/add")
    public String addPlayerScoutingReport(@ModelAttribute PlayerScoutedDto reportDto) {
        PlayerScoutingReports report = playerScoutingReportService.convertToEntity(reportDto);
        playerScoutingReportService.Save(report);
        return "redirect:/playerscoutingreports";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        PlayerScoutingReports report = playerScoutingReportService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid report ID: " + id));
        model.addAttribute("report", playerScoutingReportService.convertToDto(report));
        return "playerscoutingreports/edit";
    }

    @PostMapping("/edit/{id}")
    public String editPlayerScoutingReports(@PathVariable Long id, @ModelAttribute PlayerScoutedDto reportDto) {
        PlayerScoutingReports existingReport = playerScoutingReportService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid report ID: " + id));
        PlayerScoutingReports report = playerScoutingReportService.convertToEntity(reportDto);
        report.setId(existingReport.getId());
        playerScoutingReportService.Save(report);
        return "redirect:/playerscoutingreports";
    }

    @GetMapping("/delete/{id}")
    public String deletePlayerScoutingReport(@PathVariable Long id) {
        playerScoutingReportService.deleteById(id);
        return "redirect:/playerscoutingreport";
    }
}