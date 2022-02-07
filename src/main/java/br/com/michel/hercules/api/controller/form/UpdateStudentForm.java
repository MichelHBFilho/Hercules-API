package br.com.michel.hercules.api.controller.form;

import br.com.michel.hercules.model.Student;
import br.com.michel.hercules.repository.ResponsibleRepository;
import br.com.michel.hercules.repository.SchoolClassRepository;
import br.com.michel.hercules.repository.StudentRepository;

public class UpdateStudentForm {

	private String register;
	private String responsibleCpf;
	private Integer classNumber;
	
	public Student update(
			Student student,
			ResponsibleRepository responsibleRepository,
			SchoolClassRepository schoolClassRepository,
			StudentRepository studentRepository
	) {
		if(register != null) 
			student.setRegister(register);
		if(responsibleCpf != null) 
			student.setResponsible(responsibleRepository.findByCpf(responsibleCpf));
		if(classNumber != null)
			student.setSchoolClass(schoolClassRepository.findByClassNumber(classNumber));
		
		studentRepository.save(student);
		
		return student;
	}

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public String getResponsibleCpf() {
		return responsibleCpf;
	}

	public void setResponsibleCpf(String responsibleCpf) {
		this.responsibleCpf = responsibleCpf;
	}

	public Integer getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(Integer classNumber) {
		this.classNumber = classNumber;
	}

}
