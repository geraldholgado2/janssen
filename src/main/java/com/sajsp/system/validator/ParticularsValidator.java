package com.sajsp.system.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

public class ParticularsValidator implements ConstraintValidator<ParticularsConstraint, Object> {

	private String particulars;

	private String isMassIntention;

	@Override
	public void initialize(ParticularsConstraint constraintAnnotation) {
		this.particulars = constraintAnnotation.particulars();
		this.isMassIntention = constraintAnnotation.isMassIntention();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		String particularsValue = (String) new BeanWrapperImpl(value).getPropertyValue(particulars);
		Boolean isMassIntentionValue = (Boolean) new BeanWrapperImpl(value).getPropertyValue(isMassIntention);
		if (isMassIntentionValue.booleanValue()) {
			return true;
		}
		return StringUtils.hasText(particularsValue);
	}

}
