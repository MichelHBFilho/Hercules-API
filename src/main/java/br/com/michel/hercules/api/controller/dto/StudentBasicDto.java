package br.com.michel.hercules.api.controller.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.michel.hercules.model.Student;

public class StudentBasicDto {

	private String name;
	private String picture;
	private String register;
	private List<GradeDto> lastFiveGrades;

	public StudentBasicDto(Student student) {
		List<GradeDto> studentGrades = GradeDto.convert(student.getGrades());

		this.name = student.getName();
		this.picture = student.getStudentPicture();
		this.register = student.getRegister();
		
		if(studentGrades.size() > 5) {
			this.lastFiveGrades = studentGrades.subList(studentGrades.size()-5, studentGrades.size());
		} else 
			this.lastFiveGrades = studentGrades;
	}

	public static List<StudentBasicDto> convert(Page<Student> students) {
		return students.map(StudentBasicDto::new).toList();
	}

	public String getName() {
		return name;
	}

	public String getPicture() {
		return picture;
	}

	public String getRegister() {
		return register;
	}

	public List<GradeDto> getLastFiveGrades() {
		return lastFiveGrades;
	}

}
