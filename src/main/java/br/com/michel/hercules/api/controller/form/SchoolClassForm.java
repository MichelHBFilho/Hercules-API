package br.com.michel.hercules.api.controller.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.michel.hercules.exceptions.InvalidCPFException;
import br.com.michel.hercules.exceptions.NotFoundException;
import br.com.michel.hercules.model.Employee;
import br.com.michel.hercules.model.SchoolClass;
import br.com.michel.hercules.repository.EmployeeRepository;
import br.com.michel.hercules.validation.CPFValidatorImpl;

public class SchoolClassForm {

	@NotNull
	private Integer classNumber;
	@NotNull
	private Integer year;
	@NotBlank
	private String room;

	public SchoolClass toSchoolClass() {
		SchoolClass schoolClass = new SchoolClass();
		
		schoolClass.setClassNumber(classNumber);
		schoolClass.setRoom(room);
		schoolClass.setYear(year);
		
		return schoolClass;
	}
	
	public void setClassNumber(Integer classNumber) {
		this.classNumber = classNumber;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void setRoom(String room) {
		this.room = room;
	}
}
