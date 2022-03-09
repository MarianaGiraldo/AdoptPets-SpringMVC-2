/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Dao.PetDao;
import Models.PetBean;
import Models.PetBeanValidation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
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
public class PetController {
    private final PetBeanValidation validate_pet;
    private final PetDao petDao;

    public PetController() {
        this.validate_pet = new PetBeanValidation();
        this.petDao = new PetDao();
    }

    @RequestMapping(value = "listpets.htm", method = RequestMethod.GET)
    public ModelAndView listPets() {
        ModelAndView mav = new ModelAndView();
        List pets;
        pets = petDao.listPets();
        mav.addObject("pets", pets);
        mav.setViewName("Views/list_pets");
        
        return mav;
    }

    @RequestMapping(value = "form_pet.htm", method = RequestMethod.GET)
    public ModelAndView getPetForm(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("Views/jstlform_pet");
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            PetBean pet = this.petDao.getPetxId(id);
            mav.addObject("pet", pet);
            return mav;
        } else {
            mav.addObject("pet", new PetBean());
            return mav;
        }
    }

    /**
     * *
     * Form Validation
     *
     * @param pb
     * @param result
     * @param status
     * @return ModelAndView
     */
    @RequestMapping(value = "form_pet.htm", method = RequestMethod.POST)
    public ModelAndView valPostPetForm(
           @ModelAttribute("pet") PetBean pb,
            BindingResult result,
            SessionStatus status
    ) {
        ModelAndView mav = new ModelAndView();
        this.validate_pet.validate(pb, result);
        if (result.hasErrors()) {
            mav.addObject("pet", new PetBean());
            mav.setViewName("Views/jstlform_pet");
        } else {
            //Insert or Update on pets table
            this.petDao.savePet(pb);
            mav.addObject("pet", pb);
            mav.setViewName("Views/jstlview_pet");
        }
        return mav;
    }
    
    @RequestMapping(value = "deletepet.htm", method = RequestMethod.GET)
    public ModelAndView deletePet(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        int id = Integer.parseInt(request.getParameter("id"));
        this.petDao.deletePet(id);
        mav.setViewName("redirect:/listpets.htm");
        return mav;
    }

    
}
