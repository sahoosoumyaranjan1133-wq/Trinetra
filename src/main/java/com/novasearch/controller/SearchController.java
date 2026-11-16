package com.novasearch.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5500",
"http://127.0.0.1:5500"}) // Allow requests from the React frontend
public class SearchController {

    @GetMapping("/search")
    public Map<String, String> search(
            @RequestParam String q) {

        return Map.of(
            "query", q,
            "message", "NovaSearch is working!",
            "status", "SUCCESS"
        );
    }
}
