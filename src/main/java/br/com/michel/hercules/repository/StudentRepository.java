package br.com.michel.hercules.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.michel.hercules.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	Page<Student> findBySchoolClassClassNumber(Integer classNumber, Pageable pageable);

	Optional<Student> findByRegister(String register);
	
}
