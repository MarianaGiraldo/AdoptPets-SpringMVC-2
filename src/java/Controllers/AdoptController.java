/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controllers;

import Dao.*;
import Models.*;
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
public class AdoptController {
    private final UserDao userDao;
    private final PetDao petDao;
    private final AdoptDao adoptDao;
    private final AdoptBeanValidation validate_adopt;

    public AdoptController() {
       this.userDao = new UserDao();
       this.petDao = new PetDao();
       this.adoptDao = new AdoptDao();
       this.validate_adopt = new AdoptBeanValidation();
    }
    
    /***
     * GET adoptions form
     * @param request
     * @return ModelAndView mav
     */
    @RequestMapping(value = "form_adoptpet.htm", method = RequestMethod.GET)
    public ModelAndView getAdoptForm(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("Views/jstlform_adoptpet");
        AdoptBean ab;
        //Getting pet_id parameter
        String reqPet_id = request.getParameter("pet_id");
        mav.addObject("reqPet_id", reqPet_id);
        
        //Adding users and pets lists
        List users = userDao.listUsersLimitAdopt();
        mav.addObject("userList", users);
        List pets = petDao.listAvailablePets();
        mav.addObject("petList", pets);
        if (request.getParameter("id") != null) {
            //Update Adoption form
            int id = Integer.parseInt(request.getParameter("id"));
            ab = this.adoptDao.getAdoptionById(id);
            int code = ab.getId();
            mav.addObject("code", code);
            
            //Getting old pet
            PetBean pet = petDao.getPetxId(Integer.parseInt(ab.getPet_id()));
            pets.add(pet);
            mav.addObject("petList", pets);
            
            mav.addObject("adopt", ab);
        } else {
            //Insert Adoption form
            ab = new AdoptBean();
            int code = this.adoptDao.getCode();
            mav.addObject("code", code);

            ab.setPet_id(reqPet_id);
            mav.addObject("adopt", ab);

        }
        return mav;
    }
    
    /***
     * Validation adoptions form
     * @param ab
     * @param result
     * @param status
     * @param request
     * @return ModelAndView mav
     */
    @RequestMapping(value = "form_adoptpet.htm", method = RequestMethod.POST)
    public ModelAndView postAdoptForm(
            @ModelAttribute("adopt") AdoptBean ab,
            BindingResult result,
            SessionStatus status,
            HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        this.validate_adopt.validate(ab, result);
        if (result.hasErrors()) {
            //Adding users and pets lists
            List users = userDao.listUsersLimitAdopt();
            mav.addObject("userList", users);
            List pets = petDao.listAvailablePets();
            mav.addObject("petList", pets);
            
            //Getting pet_id parameter
            String reqPet_id = request.getParameter("pet_id");
            mav.addObject("reqPet_id", reqPet_id);
            ab = new AdoptBean();
            int code = this.adoptDao.getCode();
            mav.addObject("code", code);

            ab.setPet_id(reqPet_id);
            mav.addObject("adopt", ab);
            mav.setViewName("Views/jstlform_adoptpet");
        } else {
            this.adoptDao.saveAdoption(ab);
            mav.setViewName("Views/jstlview_adoptpet");
        }
        return mav;
    }
    
    //Listing users
    @RequestMapping("listadoptions.htm")
        public ModelAndView listAdoptions(){
        ModelAndView mav = new ModelAndView();
        List adoptions = this.adoptDao.listAdoptions();
        mav.addObject("adoptions", adoptions);
        mav.setViewName("Views/list_adoptions");
        return mav;
    }
        
    @RequestMapping(value = "deleteadoption.htm", method = RequestMethod.GET)
    public ModelAndView deleteAdoption(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        int id = Integer.parseInt(request.getParameter("id"));
        this.adoptDao.deleteAdoption(id);
        mav.setViewName("redirect:/listadoptions.htm");
        return mav;
    }
    
}
