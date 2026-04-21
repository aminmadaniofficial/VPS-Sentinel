package me.aminmadani.vps_sentinel.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemStats {
    private String memory;
    private String cpuLoad;
    private String uptime;
    private String diskUsage;
}