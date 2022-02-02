package br.com.michel.hercules.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.michel.hercules.model.User;
import br.com.michel.hercules.repository.UserRepository;

@RestController
public class HelloController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/hello")
	public List<User> users() {
		return userRepository.findAll();
	}
	
}
