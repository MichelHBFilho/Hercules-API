package br.com.michel.hercules.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.michel.hercules.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Optional<Employee> findByCpf(String cpf);
	
}
