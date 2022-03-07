/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Mariana
 */
public class AdoptBeanValidation implements Validator{

    @Override
    public boolean supports(Class<?> type) {
        return UserBean.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "user_id", "required.user_id", "The user field is required"
        );
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "pet_id", "required.pet_id", "The pet field is required"
        );
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "date", "required.date", "The date field is required"
        );
    }
    
}
