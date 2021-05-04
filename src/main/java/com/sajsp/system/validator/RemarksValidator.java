package com.sajsp.system.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

public class RemarksValidator implements ConstraintValidator<RemarksConstraint, Object> {

	private String remarks;

	private String isInCash;

	@Override
	public void initialize(RemarksConstraint constraintAnnotation) {
		this.remarks = constraintAnnotation.remarks();
		this.isInCash = constraintAnnotation.isInCash();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		String remarksValue = (String) new BeanWrapperImpl(value).getPropertyValue(remarks);
		Boolean isInCashValue = (Boolean) new BeanWrapperImpl(value).getPropertyValue(isInCash);
		if (isInCashValue.booleanValue()) {
			return true;
		}
		return StringUtils.hasText(remarksValue);
	}

}
