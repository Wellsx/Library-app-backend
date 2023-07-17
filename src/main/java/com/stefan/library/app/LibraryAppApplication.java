package com.stefan.library.app;

import java.util.HashSet;
import java.util.Set;

import com.stefan.library.app.models.ApplicationUser;
import com.stefan.library.app.models.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.stefan.library.app.repository.RoleRepository;
import com.stefan.library.app.repository.UserRepository;

@SpringBootApplication
public class LibraryAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryAppApplication.class, args);
	}

	// inject repositories and password encoder
	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository,
						  PasswordEncoder passwordEncoder) {
		return args -> {
			if (!roleRepository.findByAuthority("ADMIN").isPresent()) {
				Role adminRole = roleRepository.save(new Role("ADMIN"));
				roleRepository.save(new Role("USER"));

				Set<Role> roles = new HashSet<>();
				roles.add(adminRole);

				ApplicationUser admin = new ApplicationUser(1, "admin", passwordEncoder.encode("admin123456"), roles);
				userRepository.save(admin);
			}
		};
	}

}
