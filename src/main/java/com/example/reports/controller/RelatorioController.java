package com.example.reports.controller;

import com.example.reports.dto.SumarioDTO;
import com.example.reports.service.RelatorioService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @PostMapping("/createData/{registers}")
    public void createData(@PathVariable Integer registers) {
        relatorioService.createData(registers);
    }

    @PostMapping("/createReport")
    public void createReport(@RequestBody SumarioDTO sumario) throws IOException {
        relatorioService.createReport(sumario);

    }
}