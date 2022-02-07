package br.com.michel.hercules.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("R")
public class Responsible extends Person {
	
	@Embedded
	private Adress adress = new Adress();
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "responsible")
	private List<Student> students = new ArrayList<>();

	public Responsible() {
	}

	public Responsible(String name, 
			String cpf, User login, 
			String street, 
			String neighborhood, 
			String complement) {
		super(name, cpf, login);
		this.adress.setStreet(street);
		this.adress.setNeighborhood(neighborhood);
		this.adress.setComplement(complement);
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
