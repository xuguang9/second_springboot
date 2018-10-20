/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package xg.inclass.second_springboot.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraints.NotBlank;
import xg.inclass.second_springboot.annotation.XgNotBlank;

/***
 * T00051
 */
public class XgNotBlankValidator implements ConstraintValidator<XgNotBlank, CharSequence> {

    @Override
    public void initialize(XgNotBlank annotation) {
    }

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        if ( charSequence == null ) {
            return true;
        }

        return charSequence.toString().trim().length() > 0;
    }
}
