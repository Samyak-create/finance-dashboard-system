package com.finance.backend.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.finance.backend.entities.User;
import com.finance.backend.enums.Role;
import com.finance.backend.enums.Status;
import com.finance.backend.repository.UserRepository;

@Component
public class DataInitializer  implements CommandLineRunner{

	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		if(userRepository.findByEmail("admin@test.com").isEmpty()) {
			User admin=new User();
			admin.setName("Admin");
		    admin.setEmail("admin@test.com");
		    admin.setPassword(passwordEncoder.encode("123456"));
		    admin.setRole(Role.ADMIN);
		    admin.setStatus(Status.ACTIVE);
		    
		    userRepository.save(admin);
		    System.out.println("Default Admin Created");
		}
	}
	
}
