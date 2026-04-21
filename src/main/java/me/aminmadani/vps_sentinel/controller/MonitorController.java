package me.aminmadani.vps_sentinel.controller;

import lombok.RequiredArgsConstructor;
import me.aminmadani.vps_sentinel.model.SystemStats;
import me.aminmadani.vps_sentinel.service.MonitorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/monitor")
@RequiredArgsConstructor
public class MonitorController {

    private final MonitorService monitorService;

    @GetMapping("/stats")
    public SystemStats getStats() {
        return monitorService.getStatus();
    }

    /**
     * Endpoint for advanced metrics including processes and network ports
     */
    @GetMapping("/extended")
    public Map<String, Object> getExtendedData() {
        Map<String, Object> data = new HashMap<>();
        data.put("processes", monitorService.getTopProcesses());
        data.put("ports", monitorService.getNetworkPorts());
        return data;
    }
}