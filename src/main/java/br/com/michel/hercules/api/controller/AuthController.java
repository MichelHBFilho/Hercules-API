package br.com.michel.hercules.api.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.michel.hercules.api.controller.dto.TokenDto;
import br.com.michel.hercules.api.controller.form.LoginForm;
import br.com.michel.hercules.api.controller.form.UpdateUserForm;
import br.com.michel.hercules.config.security.TokenService;
import br.com.michel.hercules.model.User;
import br.com.michel.hercules.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/auth")
	public ResponseEntity<TokenDto> auth(
			@RequestBody @Valid LoginForm form
	) {
		
		UsernamePasswordAuthenticationToken loginData = form.convert();
		try {
			Authentication auth = authManager.authenticate(loginData);
			String token = tokenService.getToken(auth);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
		
	}
	
	@PutMapping("/auth/update")
	public ResponseEntity<?> update(
			@RequestBody @Valid UpdateUserForm form
	) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		user = form.toUser(userRepository, user);
		
		userRepository.save(user);
		
		return ResponseEntity.ok().build();
		
	}
	
}
