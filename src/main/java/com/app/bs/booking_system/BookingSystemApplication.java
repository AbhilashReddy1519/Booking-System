package com.app.bs.booking_system;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.context.WebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BookingSystemApplication {

	public static void main(String[] args) {
    java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("Asia/Kolkata"));
    SpringApplication.run(BookingSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner run(WebServerApplicationContext context) {
		return args -> {
			int port = context.getWebServer().getPort();
			System.out.println("Hey Champ ^_~");
			System.out.println("Patient Service is running at ....");
			System.out.println("http://localhost:" + port);
		};
	}

}
