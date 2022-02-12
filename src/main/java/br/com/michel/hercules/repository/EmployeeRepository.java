package br.com.michel.hercules.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.michel.hercules.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Employee findByCpf(String cpf);
	
}
