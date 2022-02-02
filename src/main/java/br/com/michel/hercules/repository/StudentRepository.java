package br.com.michel.hercules.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.michel.hercules.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	Page<Student> findBySchoolClassClassNumber(Integer classNumber, Pageable pageable);

	Student findByRegister(String register);
	
}
