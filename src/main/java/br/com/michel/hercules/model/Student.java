package br.com.michel.hercules.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("S")
public class Student extends Person {

	private String register;
	@ManyToOne(fetch = FetchType.LAZY)
	private Responsible responsible;
	@ManyToOne(fetch = FetchType.LAZY)
	private SchoolClass schoolClass;
	private String studentPicture;
	private LocalDate birthDay;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "student")
	private List<Grade> grades = new ArrayList<>();

	public Student() {
	}
	
	public Student(
			String name,
			String cpf,
			User login,
			String register,
			Responsible responsible,
			SchoolClass schoolClass,
			String studentPicture,
			LocalDate birthDay
	) {
		super(name, cpf, login);
		this.register = register;
		this.responsible = responsible;
		this.schoolClass = schoolClass;
		this.studentPicture = studentPicture;
		this.birthDay = birthDay;
	}



	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public Responsible getResponsible() {
		return responsible;
	}
	
	public Long getResponsibleId() {
		return responsible.getId();
	}

	public void setResponsible(Responsible responsible) {
		this.responsible = responsible;
	}

	public SchoolClass getSchoolClass() {
		return schoolClass;
	}

	public void setSchoolClass(SchoolClass schoolClass) {
		this.schoolClass = schoolClass;
	}

	public String getStudentPicture() {
		return studentPicture;
	}

	public void setStudentPicture(String studentPicture) {
		this.studentPicture = studentPicture;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}

	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}

}
