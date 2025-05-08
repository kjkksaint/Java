package com.cyberscan.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ScanService {

    @Value("${scanner.script.path}")
    private String scriptPath;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Async
    public CompletableFuture<ScanResult> runPythonScanAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ProcessBuilder pb = new ProcessBuilder("python3", scriptPath);
                pb.redirectErrorStream(true);

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(pb.start().getInputStream()))) {
                    StringBuilder output = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line);
                    }

                    String result = output.toString().trim();

                    if (!result.startsWith("{")) {
                        throw new RuntimeException("Output do script inválido: não é JSON.");
                    }

                    return objectMapper.readValue(result, ScanResult.class);
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro ao executar ou interpretar script Python: " + e.getMessage(), e);
            }
        });
    }

    // DTO interno
    public static class ScanResult {

        @JsonProperty("status")
        private String status;

        @JsonProperty("vulnerabilities")
        private List<String> vulnerabilities;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<String> getVulnerabilities() {
            return vulnerabilities;
        }

        public void setVulnerabilities(List<String> vulnerabilities) {
            this.vulnerabilities = vulnerabilities;
        }
    }
}
