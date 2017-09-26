package com.bhtask.nhs.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bhtask.nhs.backend.dao.Condition;
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

    @GetMapping("/all")
    public List<String> all() {
	return conditionsService.getAllLinks();
    }

    @GetMapping("/count")
    public Long count() {
	return conditionsService.getCount();
    }

    @GetMapping("/search")
    public List<Condition> search(@RequestParam("query") final String query,
	    @RequestParam(name = "includeContent", required = false, defaultValue = "true") boolean hideContent) {

	List<Condition> results = conditionsService.search(query);
	return results;
    }
}
