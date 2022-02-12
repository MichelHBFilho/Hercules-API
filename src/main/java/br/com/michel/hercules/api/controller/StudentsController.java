package br.com.michel.hercules.api.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.michel.hercules.api.controller.dto.GradeDto;
import br.com.michel.hercules.api.controller.dto.StudentBasicDto;
import br.com.michel.hercules.api.controller.dto.StudentDto;
import br.com.michel.hercules.api.controller.form.GradeForm;
import br.com.michel.hercules.api.controller.form.StudentForm;
import br.com.michel.hercules.api.controller.form.UpdateStudentForm;
import br.com.michel.hercules.exceptions.NotFoundException;
import br.com.michel.hercules.model.Grade;
import br.com.michel.hercules.model.Responsible;
import br.com.michel.hercules.model.Student;
import br.com.michel.hercules.repository.EmployeeRepository;
import br.com.michel.hercules.repository.GradeRepository;
import br.com.michel.hercules.repository.ProfileRepository;
import br.com.michel.hercules.repository.ResponsibleRepository;
import br.com.michel.hercules.repository.SchoolClassRepository;
import br.com.michel.hercules.repository.StudentRepository;
import br.com.michel.hercules.repository.SubjectRepository;

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
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private GradeRepository gradeRepository;
	@Autowired
	private ProfileRepository profileRepository;
	
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
		Optional<Student> optional = studentRepository.findByRegister(register);
		
		if(optional.isEmpty())
			throw new NotFoundException("Student");
		
		return new StudentDto(optional.get());
	}
	
	@PostMapping("/student/{register}/grades")
	public ResponseEntity<GradeDto> newGrade(
			@RequestBody GradeForm gradeForm,
			@PathVariable String register
	) {
		Optional<Student> optional = studentRepository.findByRegister(register);
		
		if(optional.isEmpty())
			throw new NotFoundException("Student");
		
		Student student = optional.get();
		
		gradeForm.setStudent(student);
		Grade grade = gradeForm.toGrade(employeeRepository, subjectRepository);
		gradeRepository.save(grade);
		
		return ResponseEntity.created(null).body(new GradeDto(grade));
	}
	
	@PostMapping(value = "/student", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<StudentDto> newStudent(
			@RequestPart(name = "file", required = false) MultipartFile file,
			@RequestPart("json") @Valid StudentForm studentForm
	) {
		studentForm.setPicture(file);
		Student student = 
				studentForm.toStudent(responsibleRepository, schoolClassRepository,
						profileRepository, resourcesPath);
		studentRepository.save(student);
		
		URI uri = URI.create("/student/" + student.getRegister());
		return ResponseEntity.created(uri).body(new StudentDto(student));
	}
	
	@DeleteMapping("/student/{register}")
	public ResponseEntity<?> deleteStudent(
			@PathVariable String register
	) {
		Optional<Student> optional = studentRepository.findByRegister(register);
		
		if(optional.isEmpty())
			throw new NotFoundException("Student");
		
		Student student = optional.get();
		
		Long responsibleId = student.getResponsible().getId();
		studentRepository.delete(student);
		
		Responsible responsible = responsibleRepository.getById(responsibleId);
		
		if(responsible.getStudents().isEmpty()) 
			responsibleRepository.delete(responsible);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/student/{register}")
	@Transactional
	public ResponseEntity<StudentDto> updateStudent(
			@RequestBody UpdateStudentForm form,
			@PathVariable String register
	) {
		Optional<Student> optional = studentRepository.findByRegister(register);
		
		if(optional.isEmpty())
			throw new NotFoundException("Student");
		
		Student student = optional.get();
		
		student = form.update(student, responsibleRepository, schoolClassRepository, studentRepository);
		
		return ResponseEntity.ok(new StudentDto(student));
	}
}
