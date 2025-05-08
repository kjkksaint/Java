package com.cyberscan.controller;

import com.cyberscan.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/scan")
public class ScanController {

    @Autowired
    private ScanService scanService;

    @GetMapping("/start")
    public Map<String, Object> startScan() throws Exception {
        return scanService.runPythonScan();
    }
}