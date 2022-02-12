package br.com.michel.hercules.model;

import javax.persistence.Embeddable;

@Embeddable
public class Adress {

	private String street;
	private String neighborhood;
	private String complement;

	public Adress() {
	}

	public Adress(String street, String neighborhood, String complement) {
		super();
		this.street = street;
		this.neighborhood = neighborhood;
		this.complement = complement;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

}
