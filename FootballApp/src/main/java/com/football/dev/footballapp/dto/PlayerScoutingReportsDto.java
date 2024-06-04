package com.football.dev.footballapp.dto;

import lombok.*;

import java.util.List;

public class PlayerScoutingReportsDto {
    private Long id;
    private String playerName;
    private String position;
    private String reportSummary;
    private String scoutName;

    // Default constructor
    public PlayerScoutingReportsDto() {
    }

    // Parameterized constructor
    public PlayerScoutingReportsDto(Long id, String playerName, String position, String reportSummary, String scoutName) {
        this.id = id;
        this.playerName = playerName;
        this.position = position;
        this.reportSummary = reportSummary;
        this.scoutName = scoutName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getReportSummary() {
        return reportSummary;
    }

    public void setReportSummary(String reportSummary) {
        this.reportSummary = reportSummary;
    }

    public String getScoutName() {
        return scoutName;
    }

    public void setScoutName(String scoutName) {
        this.scoutName = scoutName;
    }
}
