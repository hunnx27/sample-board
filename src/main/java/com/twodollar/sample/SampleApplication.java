package com.twodollar.sample;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class SampleApplication {
	

	
	@GetMapping("/main")
	String main() {
		return "Spring is here!";
	}


	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}
}