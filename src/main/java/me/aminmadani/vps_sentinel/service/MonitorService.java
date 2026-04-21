package me.aminmadani.vps_sentinel.service;

import lombok.RequiredArgsConstructor;
import me.aminmadani.vps_sentinel.model.SystemStats;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonitorService {

    public SystemStats getStatus() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return SystemStats.builder()
                    .cpuLoad("12.0%")
                    .memory("2048MB / 8192MB")
                    .uptime("1 day, 05:20:11")
                    .diskUsage("30")
                    .build();
        }

        return SystemStats.builder()
                .cpuLoad(execute("top -bn1 | grep 'Cpu(s)' | sed 's/.*, *\\([0-9.]*\\)%* id.*/\\1/' | awk '{print 100 - $1}'") + "%")
                .memory(execute("free -m | awk 'NR==2{print $3\"MB / \"$2\"MB\"}'"))
                .uptime(execute("uptime -p"))
                .diskUsage(execute("df -h / | awk 'NR==2{print $5}'").replace("%", ""))
                .build();
    }

    /**
     * Fetches top 10 CPU consuming processes
     */
    public List<String[]> getTopProcesses() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            List<String[]> mockProcs = new ArrayList<>();
            mockProcs.add(new String[]{"1234", "1.2", "5.5", "idea64.exe"});
            mockProcs.add(new String[]{"5678", "0.5", "2.1", "chrome.exe"});
            return mockProcs;
        }

        String rawOutput = execute("ps -eo pid,%mem,%cpu,comm --sort=-%cpu | head -n 11 | tail -n +2");
        return parseCommandOutput(rawOutput);
    }

    /**
     * Fetches listening network ports and services
     */
    public List<String[]> getNetworkPorts() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            List<String[]> mockPorts = new ArrayList<>();
            mockPorts.add(new String[]{"TCP", "0.0.0.0:8080", "LISTEN"});
            return mockPorts;
        }

        /* Fixed the stream type mismatch here */
        String rawOutput = execute("netstat -tuln | grep LISTEN | awk '{print $1\":\"$4\":\"$6}'");
        return rawOutput.lines()
                .filter(line -> !line.isBlank())
                .map(line -> line.split(":"))
                .collect(Collectors.toList());
    }

    private String execute(String command) {
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (Exception e) {
            return "0";
        }
        return output.toString().trim();
    }

    private List<String[]> parseCommandOutput(String raw) {
        List<String[]> list = new ArrayList<>();
        for (String line : raw.split("\n")) {
            if (!line.trim().isEmpty()) {
                list.add(line.trim().split("\\s+"));
            }
        }
        return list;
    }
}