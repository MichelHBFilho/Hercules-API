package br.com.michel.hercules.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.michel.hercules.model.User;
import br.com.michel.hercules.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> optional = userRepository.findByEmail(username);
		
		if(optional.isEmpty())
			throw new UsernameNotFoundException("Invalid data");
		
		return optional.get();
		
	}
	
}
