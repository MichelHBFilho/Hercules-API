package br.com.michel.hercules.api.controller.form;

import javax.validation.constraints.NotBlank;

import br.com.michel.hercules.model.Responsible;
import br.com.michel.hercules.model.User;

public class ResponsibleForm {

	@NotBlank
	private String name;
	@NotBlank
	private String cpf;
	@NotBlank
	private String street;
	@NotBlank
	private String neighborhood;
	private String complement = "";
	
	public Responsible toResponsible() {
		
		User user = new User();
		user.setEmail(name);
		user.setAndEncodePassword(cpf);
		
		return new Responsible(name, cpf, user, street, neighborhood, complement);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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
