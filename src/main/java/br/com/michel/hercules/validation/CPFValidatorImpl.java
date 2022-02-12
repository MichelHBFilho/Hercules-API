package br.com.michel.hercules.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.caelum.stella.validation.CPFValidator;

public class CPFValidatorImpl implements ConstraintValidator<CPF, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return isValid(value);
	}

	public static boolean isValid(String value) {
		CPFValidator cpfValidator = new CPFValidator();
		try {
			cpfValidator.assertValid(value);
			return true;
		} catch(Exception exception) {
			return false;
		}
	}
	
}
