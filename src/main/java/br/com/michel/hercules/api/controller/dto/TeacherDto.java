package br.com.michel.hercules.api.controller.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.michel.hercules.model.Employee;
import br.com.michel.hercules.model.SchoolClass;
import br.com.michel.hercules.repository.SchoolClassRepository;

public class TeacherDto {

	private String name;
	private String cpf;
	private LocalDate contractDate;
	private List<SchoolClassDto> classes;

	public TeacherDto(Employee teacher, SchoolClassRepository schoolClassRepository) {
		this.name = teacher.getName();
		this.cpf = teacher.getCpf();
		this.contractDate = teacher.getContractDate();
		
		List<SchoolClass> schoolClasses = 
				schoolClassRepository.findAll()
				.stream().filter(s -> s.getTeachers().contains(teacher)).toList();
		
		this.classes = SchoolClassDto.convert(schoolClasses);
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

	public List<SchoolClassDto> getClasses() {
		return classes;
	}

	public void setClasses(List<SchoolClassDto> classes) {
		this.classes = classes;
	}

}
