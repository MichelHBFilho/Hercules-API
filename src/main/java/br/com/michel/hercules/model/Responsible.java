package br.com.michel.hercules.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "responsibles")
@DiscriminatorValue("R")
public class Responsible extends Person {
	
	@Embedded
	private Adress adress;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "responsible")
	private List<Student> students;

	public Responsible() {
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

}
