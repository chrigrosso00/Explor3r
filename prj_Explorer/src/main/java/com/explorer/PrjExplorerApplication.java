package com.explorer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.explorer.services.AuthService;

@SpringBootApplication
public class PrjExplorerApplication implements CommandLineRunner{

	@Autowired
    private AuthService authService;
	
	public static void main(String[] args) {
		SpringApplication.run(PrjExplorerApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		authService.createAdminUser();
	}

}
