package br.com.michel.hercules.api.controller.dto;

import java.math.BigDecimal;
import java.util.List;

import br.com.michel.hercules.model.Grade;

public class GradeDto {

	private String studentName;
	private String teacherName;
	private String subject;
	private String description;
	private BigDecimal value;

	public GradeDto(Grade grade) {
		this.studentName = grade.getStudent().getName();
		this.teacherName = grade.getTeacher().getName();
		this.subject = grade.getSubject().getName();
		this.description = grade.getDescription();
		this.value = grade.getValue();
	}
	
	public static List<GradeDto> convert(List<Grade> grades) {
		return grades.stream().map(GradeDto::new).toList();
	}

	public String getStudentName() {
		return studentName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public String getSubject() {
		return subject;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getValue() {
		return value;
	}

}
