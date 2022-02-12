package br.com.michel.hercules.api.controller.dto;

import java.util.List;

import br.com.michel.hercules.model.Responsible;

public class ResponsibleDto {

	private String name;
	private String cpf;
	private String street;
	private String neighborhood;
	private String complement;
	private List<StudentDto> students;

	public ResponsibleDto(Responsible responsible) {
		
		this.name = responsible.getName();
		this.cpf = responsible.getCpf();
		this.street = responsible.getAdress().getStreet();
		this.neighborhood = responsible.getAdress().getNeighborhood();
		this.complement = responsible.getAdress().getComplement();
		this.students = StudentDto.convert(responsible.getStudents());
		
	}
	
	public String getName() {
		return name;
	}

	public String getCpf() {
		return cpf;
	}

	public String getStreet() {
		return street;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public String getComplement() {
		return complement;
	}

	public List<StudentDto> getStudents() {
		return students;
	}

}
