package com.cyberscan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.Map;

@Service
public class ScanService {

    public Map<String, Object> runPythonScan() throws Exception {
        ProcessBuilder pb = new ProcessBuilder("python3", "../python/scanner.py");
        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) output.append(line);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(output.toString(), Map.class);
    }
}