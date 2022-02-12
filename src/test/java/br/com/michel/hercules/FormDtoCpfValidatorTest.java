package br.com.michel.hercules;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;

import br.com.michel.hercules.api.controller.form.StudentForm;

class FormDtoCpfValidatorTest {

	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	@Test
	void cpfValidator() {
		StudentForm sf = new StudentForm();
		sf.setBirthDay(LocalDate.now());
		sf.setClassNumber(101);
		sf.setCpf("05668586065");
		sf.setName("james");
		sf.setRegister("124121");
		sf.setResponsibleCpf("09669386071");
		
		// here cpf is valid
		Set<ConstraintViolation<StudentForm>> violations = validator.validate(sf);
		assertTrue(violations.isEmpty());
		
		// here student cpf is invalid
		sf.setCpf("05668586064");
		violations = validator.validate(sf);
		assertTrue(violations.size() == 1);
		
		// here responsible cpf is invalid
		sf.setCpf("05668586065");
		sf.setResponsibleCpf("09669386070");
		violations = validator.validate(sf);
		assertTrue(violations.size() == 1);
	}

}
