package br.com.michel.hercules.api.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.michel.hercules.model.Employee;
import br.com.michel.hercules.model.Grade;
import br.com.michel.hercules.model.Student;
import br.com.michel.hercules.model.Subject;
import br.com.michel.hercules.repository.EmployeeRepository;
import br.com.michel.hercules.repository.SubjectRepository;

public class GradeForm {

	@NotBlank
	private String teacherCpf;
	@NotBlank
	private String subject;
	@NotBlank
	private String description;
	@NotNull
	private BigDecimal value;
	private Student student;
	
	public Grade toGrade(
		EmployeeRepository employeeRepository,
		SubjectRepository subjectRepository
	) {
		Employee teacher = employeeRepository.findByCpf(teacherCpf);
		Subject subject = subjectRepository.findByName(this.subject);
		
		return new Grade(
				student,
				teacher,
				subject,
				description,
				value
		);
		
	}

	public String getTeacherCpf() {
		return teacherCpf;
	}

	public void setTeacherCpf(String teacherCpf) {
		this.teacherCpf = teacherCpf;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
