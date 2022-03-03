package br.com.michel.hercules.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Constraint(validatedBy = CPFValidatorImpl.class)
@Target({ FIELD, METHOD, PARAMETER })
@Retention(RUNTIME)
@Documented
public @interface CPF {
	String message() default "Invalid CPF";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
