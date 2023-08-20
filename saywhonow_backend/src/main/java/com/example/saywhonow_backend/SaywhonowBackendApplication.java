package com.example.saywhonow_backend;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.saywhonow_backend.models.Role;
import com.example.saywhonow_backend.models.User;
import com.example.saywhonow_backend.repository.RoleRepository;
import com.example.saywhonow_backend.repository.UserRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
// @RestController
public class SaywhonowBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaywhonowBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode){
		return args -> {
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;

			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);
			
			User admin = new User(1, "admin", passwordEncode.encode("pass"), "email", roles);

			userRepository.save(admin);
		};
	}

	@CrossOrigin
	@GetMapping("/hello")
	public String hello(@RequestParam (value="name", defaultValue="springboot") String name){
		return String.format("Hello %s", name);
	}

}
