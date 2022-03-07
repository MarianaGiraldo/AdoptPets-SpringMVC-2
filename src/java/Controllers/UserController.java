/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controllers;

import Dao.DBConnection;
import Dao.UserDao;
import Models.UserBean;
import Models.UserBeanValidation;
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
public class UserController {
    private final UserBeanValidation validate_user;
    private final UserDao userDao;

    public UserController() {
        this.validate_user = new UserBeanValidation();
        this.userDao = new UserDao();
    }
    
    //Listing users
    @RequestMapping("listusers.htm")
    public ModelAndView listUsers(){
        ModelAndView mav = new ModelAndView();
        List users = this.userDao.listUsers();
        mav.addObject("users", users);
        mav.setViewName("Views/listusers");
        return mav;
    }
    
    //User Form Method: GET
    @RequestMapping(value = "form_user.htm", method = RequestMethod.GET)
    public ModelAndView getUserForm(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("Views/jstlform_user");
        if (request.getParameter("id") != null) {
            //Update form
            int id = Integer.parseInt(request.getParameter("id"));
            UserBean user = this.userDao.getUserById(id);
            mav.addObject("user", user);
        } else {
            //Insert form
            mav.addObject("user", new UserBean());
        }
        return mav;
    }
    
    /***
      Form Validation
     * @param ub
     * @param result
     * @param status
      @return ModelAndView
    ***/
    @RequestMapping(value = "form_user.htm", method = RequestMethod.POST)
    public ModelAndView valPostUserForm(
            @ModelAttribute("user") UserBean ub, 
            BindingResult result,
            SessionStatus status
            ){
        ModelAndView mav = new ModelAndView();
        this.validate_user.validate(ub, result);
        if(result.hasErrors()){
            mav.addObject("ub", new UserBean());
            mav.setViewName("Views/jstlform_user");
        }else{
            //Insert or Update on users table
            this.userDao.saveUser(ub);
            mav.addObject("ub", ub);
            mav.setViewName("Views/jstlview_user");
        }
        return mav;
    }
    
    @RequestMapping(value = "deleteuser.htm", method = RequestMethod.GET)
    public ModelAndView deleteUser(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        int id = Integer.parseInt(request.getParameter("id"));
        this.userDao.deleteUser(id);
        mav.setViewName("redirect:/listusers.htm");
        return mav;
    }
    
    
    
}
