package br.com.michel.hercules.api.controller.form;

import javax.validation.constraints.NotBlank;

import br.com.michel.hercules.model.Employee;
import br.com.michel.hercules.model.User;
import br.com.michel.hercules.repository.ProfileRepository;
import br.com.michel.hercules.validation.CPF;

public class EmployeeForm {

	@NotBlank
	private String name;
	@CPF
	private String cpf;

	public Employee toEmployee(ProfileRepository profileRepository, String role) {
		User user = new User();
		user.setAndEncodePassword(cpf);
		user.setEmail(name);
		user.addProfile(profileRepository.findByAuthority(role));
		return new Employee(name, cpf, user);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
