package br.com.michel.hercules.api.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.michel.hercules.api.controller.dto.EmployeeDto;
import br.com.michel.hercules.api.controller.dto.TeacherDto;
import br.com.michel.hercules.api.controller.form.EmployeeForm;
import br.com.michel.hercules.exceptions.NotFoundException;
import br.com.michel.hercules.model.Employee;
import br.com.michel.hercules.repository.EmployeeRepository;
import br.com.michel.hercules.repository.ProfileRepository;
import br.com.michel.hercules.repository.SchoolClassRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private SchoolClassRepository schoolClassRepository;

	@GetMapping("/employee/{employeeId}")
	public ResponseEntity<EmployeeDto> findEmployee(@PathVariable String employeeId) throws IsATeacher {
		Optional<Employee> optional = employeeRepository.findById(Long.parseLong(employeeId));

		if (optional.isEmpty())
			throw new NotFoundException("Employee");

		Employee employee = optional.get();

		if (employee.getLogin().getAuthorities().contains(profileRepository.findByAuthority("ROLE_TEACHER"))) {
			throw new IsATeacher(employee);
		}

		return ResponseEntity.ok(new EmployeeDto(employee));
	}

	@PostMapping("/employee")
	public ResponseEntity<EmployeeDto> newEmployee(
			@RequestBody @Valid EmployeeForm employeeForm,
			@RequestParam(required = false) String stringIsTeacher
	) throws URISyntaxException {
		Employee employee;
		boolean isTeacher = Boolean.valueOf(stringIsTeacher);
		employee = employeeForm.toEmployee(profileRepository, isTeacher);
		employeeRepository.save(employee);
		return ResponseEntity
				.created(new URI("/api/employee/" + employee.getId())).body(new EmployeeDto(employee));
	}

	@DeleteMapping("/employee/{id}")
	public ResponseEntity<?> deleteEmployee(
			@PathVariable String id
	) {
		Optional<Employee> optional = employeeRepository.findById(Long.parseLong(id));
		if(optional.isEmpty())
			throw new NotFoundException("Employee");
		employeeRepository.delete(optional.get());
		return ResponseEntity.ok().build();
	}
	
	private class IsATeacher extends Throwable {
		private static final long serialVersionUID = 4436766798966814353L;
		private Employee teacher;

		public IsATeacher(Employee teacher) {
			this.teacher = teacher;
		}

		public Employee getTeacher() {
			return teacher;
		}
	}

	@ExceptionHandler(IsATeacher.class)
	public ResponseEntity<TeacherDto> findTeacher(final IsATeacher exception) {
		Employee employee = exception.getTeacher();
		return ResponseEntity.ok(new TeacherDto(employee, schoolClassRepository));
	}

}
