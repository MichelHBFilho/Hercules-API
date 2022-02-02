package br.com.michel.hercules.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.michel.hercules.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	List<Student> findBySchoolClassClassNumber(Integer classNumber);

	Student findByRegister(String register);
	
}
