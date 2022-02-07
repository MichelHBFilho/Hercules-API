package br.com.michel.hercules.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.michel.hercules.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	
	Profile findByAuthority(String authority);
	
}
