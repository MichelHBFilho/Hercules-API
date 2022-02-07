package br.com.michel.hercules.api.controller.dto;

import java.time.LocalDate;
import java.util.List;

import br.com.michel.hercules.model.Student;

public class StudentDto {

	private String name;
	private String responsibleName;
	private Integer classNumber;
	private Integer year;
	private String picture;
	private LocalDate birthDay;
	private List<GradeDto> grades;
	private String register;

	public StudentDto(Student student) {
		this.name = student.getName();
		this.responsibleName = student.getResponsible().getName();
		this.classNumber = student.getSchoolClass().getClassNumber();
		this.year = student.getSchoolClass().getYear();
		this.picture = student.getStudentPicture();
		this.birthDay = student.getBirthDay();
		this.grades = GradeDto.convert(student.getGrades());
		this.register = student.getRegister();
	}

	public String getName() {
		return name;
	}

	public String getResponsibleName() {
		return responsibleName;
	}

	public Integer getClassNumber() {
		return classNumber;
	}

	public Integer getYear() {
		return year;
	}

	public String getPicture() {
		return picture;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public List<GradeDto> getGrades() {
		return grades;
	}

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

}
