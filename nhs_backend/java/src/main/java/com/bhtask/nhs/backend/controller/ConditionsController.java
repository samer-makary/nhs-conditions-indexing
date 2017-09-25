package com.bhtask.nhs.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bhtask.nhs.backend.service.ConditionsService;

@RestController
@RequestMapping("/conditions")
public class ConditionsController {

    @Autowired
    private ConditionsService conditionsService;

    @GetMapping("/alive")
    public String alive() {
	return "Yes!";
    }
}
