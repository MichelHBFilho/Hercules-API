package br.com.michel.hercules.api.controller.dto;

import java.math.BigDecimal;
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
	private String register;
	private BigDecimal average;
	private List<GradeDto> grades;

	public StudentDto(Student student) {
		this.name = student.getName();
		this.responsibleName = student.getResponsible().getName();
		this.classNumber = student.getSchoolClass().getClassNumber();
		this.year = student.getSchoolClass().getYear();
		this.picture = student.getStudentPicture();
		this.birthDay = student.getBirthDay();
		this.register = student.getRegister();
		this.grades = GradeDto.convert(student.getGrades());
		
		if(grades.size() < 1) return;
		
		BigDecimal sumGrades = BigDecimal.ZERO;
		
		for(GradeDto i : grades)
			sumGrades = sumGrades.add(i.getValue());
		
		this.average = sumGrades.divide(new BigDecimal(grades.size()));
	}
	
	public static List<StudentDto> convert(List<Student> students) {
		return students.stream().map(StudentDto::new).toList();
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

	public BigDecimal getAverage() {
		return average;
	}

	public void setAverage(BigDecimal average) {
		this.average = average;
	}

}
