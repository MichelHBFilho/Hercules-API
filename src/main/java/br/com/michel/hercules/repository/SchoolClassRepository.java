package br.com.michel.hercules.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.michel.hercules.model.SchoolClass;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

	SchoolClass findByClassNumber(Integer classNumber);
	
}
