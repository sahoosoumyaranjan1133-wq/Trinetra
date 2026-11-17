package com.novasearch.controller;

import java.util.List;
import java.util.Map;

import com.novasearch.service.SearchService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {
    "http://localhost:5500",
    "http://127.0.0.1:5500"
})
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam String q)
            throws Exception {

        List<String> results = searchService.search(q);

        return Map.of(
            "query", q,
            "results", results,
            "totalResults", results.size(),
            "status", "SUCCESS"
        );
    }
}
