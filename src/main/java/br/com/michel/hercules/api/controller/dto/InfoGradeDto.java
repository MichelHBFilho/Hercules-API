package br.com.michel.hercules.api.controller.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InfoGradeDto {

	private BigDecimal average;
	private List<GradeDto> grades = new ArrayList<>();
	
	public InfoGradeDto(List<GradeDto> grades) {
		this.grades = grades;
		List<BigDecimal> values = grades.stream().map(g -> g.getValue()).toList();
		BigDecimal average = BigDecimal.ZERO;
		
		for (BigDecimal value : values) 
			average = average.add(value);
		
		average = average.divide(new BigDecimal(values.size()));
		
		this.average = average;
	}
	
	public BigDecimal getAverage() {
		return average;
	}
	
	public List<GradeDto> getGrades() {
		return grades;
	}
	
}
