package br.com.michel.hercules.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.michel.hercules.api.filters.ResponsibleInfoFilter;
import br.com.michel.hercules.api.filters.StudentInfoFilter;
import br.com.michel.hercules.repository.ProfileRepository;
import br.com.michel.hercules.repository.StudentRepository;
import br.com.michel.hercules.repository.UserRepository;

@EnableWebSecurity
@Configuration
@Profile({"Test", "Prod"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private AuthService authService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private StudentRepository studentRepository;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(authService)
			.passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
			.antMatchers("/api/class/**").hasRole("EMPLOYEE")
			.antMatchers("/api/students/**").hasRole("EMPLOYEE")
			.antMatchers("/api/student/**").authenticated()
			.antMatchers("/api/responsible/**").hasAnyRole("RESPONSIBLE", "EMPLOYEE")
			.antMatchers("/api/auth/update").authenticated()
			.antMatchers("/api/employee/**").hasRole("EMPLOYEE")
			.anyRequest().permitAll()
			.and()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(new AuthViaTokenFilter(userRepository, tokenService), UsernamePasswordAuthenticationFilter.class)
			.addFilterAfter(new ResponsibleInfoFilter(), AuthViaTokenFilter.class)
			.addFilterAfter(new StudentInfoFilter(studentRepository, profileRepository), ResponsibleInfoFilter.class);
			
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		
		
	}
	
}
