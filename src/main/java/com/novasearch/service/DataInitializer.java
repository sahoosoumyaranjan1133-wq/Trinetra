package com.novasearch.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final SearchService searchService;

    public DataInitializer(SearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public void run(String... args) throws Exception {

        searchService.indexDocument(
                "Java Programming",
                "https://example.com/java",
                "Java is a popular programming language used for software development.");

        searchService.indexDocument(
                "Python Programming",
                "https://example.com/python",
                "Python is a programming language used in artificial intelligence and data science.");

        searchService.indexDocument(
                "Spring Boot Tutorial",
                "https://example.com/spring",
                "Spring Boot is a Java framework used to build web applications and REST APIs.");

        System.out.println("Trinetra: Sample documents indexed!");
    }
}
