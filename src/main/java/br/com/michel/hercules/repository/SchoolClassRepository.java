package br.com.michel.hercules.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.michel.hercules.api.controller.dto.SchoolClassDto;
import br.com.michel.hercules.model.Employee;
import br.com.michel.hercules.model.SchoolClass;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

	SchoolClass findByClassNumber(Integer classNumber);
	
}
