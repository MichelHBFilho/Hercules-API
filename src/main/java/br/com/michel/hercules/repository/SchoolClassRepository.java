package br.com.michel.hercules.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.michel.hercules.model.SchoolClass;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

	Optional<SchoolClass> findByClassNumber(Integer classNumber);
	
}
