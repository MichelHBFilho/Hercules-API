package br.com.michel.hercules.api.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.michel.hercules.model.User;
import br.com.michel.hercules.repository.ProfileRepository;

public class ResponsibleInfoFilter extends OncePerRequestFilter {

	private ProfileRepository profileRepository;
	
	public ResponsibleInfoFilter(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String path = request.getRequestURI().replace("%20", " ");
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(path.endsWith(user.getPerson().getName()) || 
				user.getAuthorities().contains(profileRepository.findByAuthority("ROLE_EMPLOYEE"))) 
			filterChain.doFilter(request, response);
		
		response.sendError(401, "Unauthorized");
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		String method = request.getMethod();
		
		if(path.startsWith("/api/responsible") && method.equals("GET"))
			return false;
		
		return true;
	}
	
}
