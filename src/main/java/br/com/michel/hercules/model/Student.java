package br.com.michel.hercules.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String register;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Responsible responsible;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User login;
	@ManyToOne(fetch = FetchType.LAZY)
	private SchoolClass schoolClass;
	private String cpf;
	private String studentPicture;
	private LocalDate birthDay;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Grade> grades = new ArrayList<>();

	public Student() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setResponsible(Responsible responsible) {
		this.responsible = responsible;
	}

	public User getLogin() {
		return login;
	}

	public void setLogin(User login) {
		this.login = login;
	}

	public SchoolClass getSchoolClass() {
		return schoolClass;
	}

	public void setSchoolClass(SchoolClass schoolClass) {
		this.schoolClass = schoolClass;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

}
