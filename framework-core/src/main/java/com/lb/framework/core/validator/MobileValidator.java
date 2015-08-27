/**
 * 
 */
package com.lb.framework.core.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.lb.framework.core.validator.constraints.Mobile;

/**
 * 
 * @author lb
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {

    private final static String mobile = "^1\\d{10}$";

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
     * Annotation)
     */
    @Override
    public void initialize(Mobile constraintAnnotation) {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
     * javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) {
            return true;
        }
        if (value.matches(mobile)) {
            return true;
        } else {
            return false;
        }
    }

}
