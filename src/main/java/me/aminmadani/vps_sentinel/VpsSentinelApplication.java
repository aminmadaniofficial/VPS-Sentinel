package me.aminmadani.vps_sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VpsSentinelApplication {

	public static void main(String[] args) {
		SpringApplication.run(VpsSentinelApplication.class, args);
	}

}
