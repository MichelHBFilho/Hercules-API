package br.com.michel.hercules.api.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.michel.hercules.api.controller.dto.GradeDto;
import br.com.michel.hercules.api.controller.dto.StudentBasicDto;
import br.com.michel.hercules.api.controller.dto.StudentDto;
import br.com.michel.hercules.api.controller.form.StudentForm;
import br.com.michel.hercules.model.Grade;
import br.com.michel.hercules.model.Student;
import br.com.michel.hercules.repository.ResponsibleRepository;
import br.com.michel.hercules.repository.SchoolClassRepository;
import br.com.michel.hercules.repository.StudentRepository;

@RestController
@RequestMapping("/api")
public class StudentsController {

	@Value("${michel.hercules.resources.path}")
	private String resourcesPath;
	
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ResponsibleRepository responsibleRepository;
	@Autowired
	private SchoolClassRepository schoolClassRepository;
	
	@GetMapping("/students/{schoolClass}")
	public List<StudentBasicDto> listSchoolClass(
			@PathVariable(name = "schoolClass") Integer classNumber,
			@PageableDefault Pageable pageable
	) {
		Page<Student> students = studentRepository.findBySchoolClassClassNumber(classNumber, pageable);
		return StudentBasicDto.convert(students);
	}
	
	@GetMapping("/students/{schoolClass}/grades")
	public List<GradeDto> listGradesSchoolClass(
			@PathVariable(name = "schoolClass") Integer classNumber,
			@PageableDefault Pageable pageable
	) {
		Page<Student> students = studentRepository.findBySchoolClassClassNumber(classNumber, pageable);
		List<Grade> grades = new ArrayList<>();
		students.forEach(s -> grades.addAll(s.getGrades()));
		
		return GradeDto.convert(grades);
	}
	
	@GetMapping("/student/{register}")
	public StudentDto findStudent(
			@PathVariable String register
	) {
		Student student = studentRepository.findByRegister(register);
		return new StudentDto(student);
	}
	
	@PostMapping(value = "/student", consumes = {"*/*"})
	public ResponseEntity<StudentDto> newStudent(
			@RequestPart(name = "file", required = false) MultipartFile file,
			@RequestPart("json") @Valid StudentForm studentForm
	) {
		studentForm.setPicture(file);
		Student student = 
				studentForm.toStudent(responsibleRepository, schoolClassRepository, resourcesPath);
		studentRepository.save(student);
		
		URI uri = URI.create("/student/" + student.getRegister());
		return ResponseEntity.created(uri).body(new StudentDto(student));
	}
	
}
