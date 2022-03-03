package br.com.michel.hercules.api.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.michel.hercules.model.Profile;
import br.com.michel.hercules.model.Responsible;
import br.com.michel.hercules.model.Student;
import br.com.michel.hercules.model.User;
import br.com.michel.hercules.repository.ProfileRepository;
import br.com.michel.hercules.repository.StudentRepository;

public class StudentInfoFilter extends OncePerRequestFilter {

	private StudentRepository studentRepository;
	private ProfileRepository profileRepository;

	public StudentInfoFilter(StudentRepository studentRepository, ProfileRepository profileRepository) {
		this.studentRepository = studentRepository;
		this.profileRepository = profileRepository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String studentRegister = request.getRequestURI().split("/")[3];
		Optional<Student> optional = studentRepository.findByRegister(studentRegister);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(optional.isEmpty())
			response.sendError(404, "Not Found");
		
		Student student = optional.get();
		
		if(Util.isEmployee(user)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		boolean canGet = student.getLoginId().equals(user.getId()) ||
				student.getResponsibleId().equals(user.getPersonId());
		
		if(canGet && request.getMethod() == "GET") {
			filterChain.doFilter(request, response);
			return;
		}
		
		response.sendError(401, "Unauthorized");
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return !request.getRequestURI().startsWith("/api/student/");
	}

}
