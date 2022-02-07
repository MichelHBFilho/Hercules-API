package br.com.michel.hercules.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.michel.hercules.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String username);

}
