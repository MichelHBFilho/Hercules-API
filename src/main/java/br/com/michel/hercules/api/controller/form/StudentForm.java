package br.com.michel.hercules.api.controller.form;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import br.com.michel.hercules.exceptions.InvalidContentTypeException;
import br.com.michel.hercules.exceptions.NotFoundException;
import br.com.michel.hercules.model.Responsible;
import br.com.michel.hercules.model.SchoolClass;
import br.com.michel.hercules.model.Student;
import br.com.michel.hercules.model.User;
import br.com.michel.hercules.repository.ProfileRepository;
import br.com.michel.hercules.repository.ResponsibleRepository;
import br.com.michel.hercules.repository.SchoolClassRepository;
import br.com.michel.hercules.validation.CPF;

public class StudentForm {
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@NotBlank
	private String name;
	@Email
	private String email;
	@CPF
	private String cpf;
	@NotBlank
	private String register;
	@NotNull
	private LocalDate birthDay;
	@NotBlank
	@CPF
	private String responsibleCpf;
	@NotNull
	private Integer classNumber;
	private MultipartFile picture;
	
	public Student toStudent(
			ResponsibleRepository responsibleRepository,
			SchoolClassRepository schoolClassRepository,
			ProfileRepository profileRepository,
			String resourcesPath
	) {
		Responsible responsible = responsibleRepository.findByCpf(this.responsibleCpf);
		Optional<SchoolClass> optional = schoolClassRepository.findByClassNumber(this.classNumber);
		
		if(optional.isEmpty()) 
			throw new NotFoundException("School Class");
		
		SchoolClass schoolClass = optional.get();
		
		User user = new User();
		user.setEmail(this.email);
		user.setAndEncodePassword(this.cpf);
		user.addProfile(profileRepository.findByAuthority("ROLE_STUDENT"));
		
		String path;
		
		if(this.picture == null) path = "/pics/defaultPhoto.jpg";
		else {
			path = "/pics/" + this.register + "." + picture.getContentType().split("/")[1];
			
			if(!picture.getContentType().startsWith("image/")) 
				throw new InvalidContentTypeException();
			
			try {
				Files.copy(this.picture.getInputStream(), 
					new File(resourcesPath + path).toPath());
			} catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return new Student(
				this.name,
				this.cpf,
				user,
				this.register,
				responsible,
				schoolClass,
				path,
				this.birthDay
		);
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

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}

	public String getResponsibleCpf() {
		return responsibleCpf;
	}

	public void setResponsibleCpf(String responsibleCpf) {
		this.responsibleCpf = responsibleCpf;
	}

	public MultipartFile getPicture() {
		return picture;
	}

	public void setPicture(MultipartFile picture) {
		this.picture = picture;
	}

	public Integer getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(Integer classNumber) {
		this.classNumber = classNumber;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}

}
