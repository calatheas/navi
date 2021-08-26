package com.calathea.navi.controller;

import org.springframework.web.bind.annotation.GetMapping;

@ApiController
public class HealthController {
    @GetMapping("/health")
    public String checkHealth(){
        return "Active";
    }
}
