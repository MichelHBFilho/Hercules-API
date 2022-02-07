package br.com.michel.hercules.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.michel.hercules.model.User;
import br.com.michel.hercules.repository.UserRepository;

public class AuthViaTokenFilter extends OncePerRequestFilter {

	private UserRepository userRepository;
	private TokenService tokenService;
	
	public AuthViaTokenFilter(UserRepository userRepository, TokenService tokenService) {
		this.userRepository = userRepository;
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = getToken(request);
		boolean valid = tokenService.isTokenValid(token);
		
		if(valid)
			authClient(token);
		
		filterChain.doFilter(request, response);
		
	}

	private void authClient(String token) {
		
		Long userId = tokenService.getUserId(token);
		User user = userRepository.findById(userId).get();
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}

	private String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if(token == null || token.isEmpty() || !token.startsWith("Bearer"))
			return null;
		
		return token.substring(7, token.length());
	}
	
}
