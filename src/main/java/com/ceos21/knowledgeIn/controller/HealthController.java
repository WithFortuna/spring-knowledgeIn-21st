package com.ceos21.knowledgeIn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/api/v1/health")
    public String healthCheck() {
        return "it's healthy";
    }
}
