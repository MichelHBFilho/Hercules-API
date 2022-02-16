package br.com.michel.hercules.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity update(
			@RequestBody @Valid UpdateUserForm form
	) {
		
		LoginForm loginForm = new LoginForm();
		loginForm.setEmail(form.getEmail());
		loginForm.setPassword(form.getPassword());
		this.auth(loginForm);
		
		User user = form.toUser(userRepository);
		
		userRepository.save(user);
		
		return ResponseEntity.ok().build();
		
	}
	
}
