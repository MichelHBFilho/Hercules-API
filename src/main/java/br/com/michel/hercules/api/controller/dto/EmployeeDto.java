package br.com.michel.hercules.api.controller.dto;

import java.time.LocalDate;

import br.com.michel.hercules.model.Employee;

public class EmployeeDto {

	private String name;
	private String cpf;
	private LocalDate contractDate;

	public EmployeeDto(Employee employee) {
		this.name = employee.getName();
		this.cpf = employee.getCpf();
		this.contractDate = employee.getContractDate();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getContractDate() {
		return contractDate;
	}

	public void setContractDate(LocalDate contractDate) {
		this.contractDate = contractDate;
	}

}
