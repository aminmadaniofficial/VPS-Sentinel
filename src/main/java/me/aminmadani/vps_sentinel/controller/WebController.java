package me.aminmadani.vps_sentinel.controller;

import me.aminmadani.vps_sentinel.service.MonitorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private final MonitorService monitorService;

    public WebController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }
    @GetMapping("/login")
    public String login() {
        return "login"; // Points to login.html
    }
    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        model.addAttribute("stats", monitorService.getStatus());
        return "dashboard";
    }

}