package br.com.michel.hercules.api.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.michel.hercules.api.controller.form.SchoolClassForm;
import br.com.michel.hercules.exceptions.NotFoundException;
import br.com.michel.hercules.model.Employee;
import br.com.michel.hercules.model.SchoolClass;
import br.com.michel.hercules.repository.EmployeeRepository;
import br.com.michel.hercules.repository.SchoolClassRepository;
import br.com.michel.hercules.validation.CPF;

@Controller
@RequestMapping("/api")
public class SchoolClassController {

	@Autowired
	private SchoolClassRepository schoolClassRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@PostMapping("/class")
	public ResponseEntity<?> newClass(
			@RequestBody @Valid SchoolClassForm form
	) throws Exception {
		SchoolClass schoolClass = form.toSchoolClass();
		schoolClassRepository.save(schoolClass);
		return ResponseEntity.created(new URI("/students/" + schoolClass.getClassNumber())).build();
	}
	
	@PostMapping("/class/{classNumber}/addTeacher")
	public ResponseEntity<?> addTeacher(
			@PathVariable String classNumber,
			@RequestBody @CPF String teacherCPF
	) {
		Optional<SchoolClass> schoolClassOptional = 
				schoolClassRepository.findByClassNumber(Integer.parseInt(classNumber));
		
		if(schoolClassOptional.isEmpty())
			throw new NotFoundException("School Class");
		
		SchoolClass schoolClass = schoolClassOptional.get();
		
		Optional<Employee> employeeOptional =
				employeeRepository.findByCpf(teacherCPF);
		
		if(employeeOptional.isEmpty())
			throw new NotFoundException("Employee");
		
		Employee employee = employeeOptional.get();
		
		schoolClass.addTeacher(employee);
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/class/{classNumber}/deleteTeacher")
	public ResponseEntity<?> deleteTeacher(
			@PathVariable String classNumber,
			@RequestParam @CPF String teacherCPF
	) {
		Optional<SchoolClass> schoolClassOptional = 
				schoolClassRepository.findByClassNumber(Integer.parseInt(classNumber));
		
		if(schoolClassOptional.isEmpty())
			throw new NotFoundException("School Class");
		
		SchoolClass schoolClass = schoolClassOptional.get();
		
		Optional<Employee> employeeOptional =
				employeeRepository.findByCpf(teacherCPF);
		
		if(employeeOptional.isEmpty())
			throw new NotFoundException("Employee");
		
		Employee employee = employeeOptional.get();
		
		schoolClass.deleteTeacher(employee);
		
		return ResponseEntity.ok().build();
	}
	
}
