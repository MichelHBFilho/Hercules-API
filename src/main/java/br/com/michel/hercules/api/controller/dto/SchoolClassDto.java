package br.com.michel.hercules.api.controller.dto;

import java.math.BigDecimal;
import java.util.List;

import br.com.michel.hercules.model.SchoolClass;

public class SchoolClassDto {

	private Integer classNumber;
	private Integer year;
	private String room;
	private BigDecimal studentsAverage;

	public SchoolClassDto(SchoolClass schoolClass) {
		this.classNumber = schoolClass.getClassNumber();
		this.year = schoolClass.getYear();
		this.room = schoolClass.getRoom();
		this.studentsAverage = schoolClass.getAverage();
	}
	
	public static List<SchoolClassDto> convert(List<SchoolClass> classes) {
		return classes.stream().map(SchoolClassDto::new).toList();
	}

	public Integer getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(Integer classNumber) {
		this.classNumber = classNumber;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public BigDecimal getStudentsAverage() {
		return studentsAverage;
	}

	public void setStudentsAverage(BigDecimal studentsAverage) {
		this.studentsAverage = studentsAverage;
	}

}
