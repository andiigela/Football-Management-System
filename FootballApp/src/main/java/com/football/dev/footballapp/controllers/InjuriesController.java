package com.football.dev.footballapp.controllers;

import com.football.dev.footballapp.services.InjuryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/injuries")
public class InjuriesController {
    private final InjuryService injuryService;
    public InjuriesController(InjuryService injuryService){
        this.injuryService=injuryService;
    }
}
