package com.sajsp.system.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = RemarksValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RemarksConstraint {

	String message() default "Remarks cannot be missing or empty";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String remarks();

	String isInCash();
}
