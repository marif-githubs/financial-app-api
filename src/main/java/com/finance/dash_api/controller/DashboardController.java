package com.finance.dash_api.controller;

import com.finance.dash_api.DTO.DashboardSummary;
import com.finance.dash_api.service.DashboardServices;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardServices service;

    public DashboardController(DashboardServices service) {
        this.service = service;
    }

    @GetMapping("/summary")
    public DashboardSummary getSummary() {
        return service.getSummary();
    }
}
