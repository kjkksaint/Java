package com.cyberscan.controller;

import com.cyberscan.service.ScanService;
import com.cyberscan.service.ScanService.ScanResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/scan")
public class ScanController {

    private final ScanService scanService;

    public ScanController(ScanService scanService) {
        this.scanService = scanService;
    }

    @GetMapping("/start")
    public CompletableFuture<ResponseEntity<ScanResult>> startScan() {
        return scanService.runPythonScanAsync()
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    // Logaria aqui no mundo real
                    return ResponseEntity.status(500).body(null); // ou DTO de erro
                });
    }
}
