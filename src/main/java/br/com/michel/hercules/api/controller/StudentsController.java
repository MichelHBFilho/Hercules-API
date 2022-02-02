package br.com.michel.hercules.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.michel.hercules.api.controller.dto.GradeDto;
import br.com.michel.hercules.api.controller.dto.InfoGradeDto;
import br.com.michel.hercules.api.controller.dto.StudentBasicDto;
import br.com.michel.hercules.api.controller.dto.StudentDto;
import br.com.michel.hercules.model.Grade;
import br.com.michel.hercules.model.Student;
import br.com.michel.hercules.repository.StudentRepository;

@RestController
@RequestMapping("/api")
public class StudentsController {

	@Autowired
	private StudentRepository studentRepository;
	
	@GetMapping("/students/{schoolClass}")
	public List<StudentBasicDto> listSchoolClass(@PathVariable(name = "schoolClass") Integer classNumber) {
		List<Student> students = studentRepository.findBySchoolClassClassNumber(classNumber);
		return StudentBasicDto.convert(students);
	}
	
	@GetMapping("/students/{schoolClass}/grades")
	public InfoGradeDto listGradesSchoolClass(@PathVariable(name = "schoolClass") Integer classNumber) {
		List<Student> students = studentRepository.findBySchoolClassClassNumber(classNumber);
		List<Grade> grades = new ArrayList<>();
		students.forEach(s -> grades.addAll(s.getGrades()));
		
		return new InfoGradeDto(GradeDto.convert(grades));
	}
	
	@GetMapping("/student/{register}")
	public StudentDto findStudent(@PathVariable String register) {
		Student student = studentRepository.findByRegister(register);
		return new StudentDto(student);
	}
	
}
