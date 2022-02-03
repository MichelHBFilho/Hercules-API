package br.com.michel.hercules.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.michel.hercules.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
		
	Subject findByName(String name);
	
}
