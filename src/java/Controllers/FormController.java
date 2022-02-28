/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controllers;

import Models.AdoptBean;
import Models.PetBean;
import Models.PetBeanValidation;
import Models.UserBean;
import Models.UserBeanValidation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Mariana
 */
@Controller
public class FormController {
    private final PetBeanValidation validate_pet;
    private final UserBeanValidation validate_user;

    public FormController() {
        this.validate_pet = new PetBeanValidation();
        this.validate_user = new UserBeanValidation();
    }
    
    
    
    
}
