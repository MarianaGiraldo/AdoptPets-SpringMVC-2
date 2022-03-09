/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controllers;

import Dao.DBConnection;
<<<<<<< HEAD
import Dao.UserDao;
import Models.UserBean;
import Models.UserBeanValidation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
=======
import Models.PetBean;
import Models.UserBean;
import Models.UserBeanValidation;
import java.sql.ResultSet;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
>>>>>>> 214b4f8724da09cb4f990380c37b7522946c8eed
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
<<<<<<< HEAD
    private final UserBeanValidation validate_user;
    private final UserDao userDao;

    public UserController() {
        this.validate_user = new UserBeanValidation();
        this.userDao = new UserDao();
=======
    private final JdbcTemplate jdbcTemplate;
    private final UserBeanValidation validate_user;

    public UserController() {
        DBConnection con = new DBConnection();
        this.jdbcTemplate = new JdbcTemplate(con.connect());
        this.validate_user = new UserBeanValidation();
>>>>>>> 214b4f8724da09cb4f990380c37b7522946c8eed
    }
    
    //Listing users
    @RequestMapping("listusers.htm")
    public ModelAndView listUsers(){
        ModelAndView mav = new ModelAndView();
<<<<<<< HEAD
        List users = this.userDao.listUsers();
=======
        
        String sql = "SELECT * from users";
        List users = this.jdbcTemplate.queryForList(sql);
>>>>>>> 214b4f8724da09cb4f990380c37b7522946c8eed
        mav.addObject("users", users);
        mav.setViewName("Views/listusers");
        return mav;
    }
    
<<<<<<< HEAD
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
=======
    @RequestMapping(value = "form_user.htm", method = RequestMethod.GET)
    public ModelAndView getUserForm(HttpServletRequest request){
       ModelAndView mav = new ModelAndView("Views/jstlform_user");
        if (request.getParameter("id") != null) {
            String id = request.getParameter("id");
            System.out.println(id);
            UserBean user = getUserById(id);
            mav.addObject("user", user);
            return mav;
        } else {
            mav.addObject("user", new UserBean());
            return mav;
        }
>>>>>>> 214b4f8724da09cb4f990380c37b7522946c8eed
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
<<<<<<< HEAD
            //Insert or Update on users table
            this.userDao.saveUser(ub);
=======
            String sql;
            String id = ub.getId();
            System.out.println("Id Post:" + id);
            // Check if pet exists
            if (getUserById(id) != null) {
                sql = "UPDATE `users` SET id = ?, name = ?, phoneNumber = ?, email = ? WHERE id = " + ub.getId();
                System.out.println(sql);
            } else {
                sql = "INSERT INTO users(id, name, phoneNumber, email) VALUES (?, ?, ?, ?)";
            }
            this.jdbcTemplate.update(sql, ub.getId(), ub.getName(), ub.getPhoneNumber(), ub.getEmail());
            
>>>>>>> 214b4f8724da09cb4f990380c37b7522946c8eed
            mav.addObject("ub", ub);
            mav.setViewName("Views/jstlview_user");
        }
        return mav;
    }
    
    @RequestMapping(value = "deleteuser.htm", method = RequestMethod.GET)
    public ModelAndView deleteUser(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
<<<<<<< HEAD
        int id = Integer.parseInt(request.getParameter("id"));
        this.userDao.deleteUser(id);
=======
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String sqlAdopt = "DELETE FROM `user_pet` WHERE `user_pet`.`user_id` = ?";
            this.jdbcTemplate.update(sqlAdopt, id);
            String sql = "DELETE from users WHERE id = ?";
            this.jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.print(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.print(e.getMessage());
        }
>>>>>>> 214b4f8724da09cb4f990380c37b7522946c8eed
        mav.setViewName("redirect:/listusers.htm");
        return mav;
    }
    
<<<<<<< HEAD
    
=======
    public UserBean getUserById(String id) {
        UserBean ub = new UserBean();
        String sql = "SELECT * from users WHERE id =" + id ;
        return (UserBean) this.jdbcTemplate.query(
                sql, (ResultSet rs) -> {
                    if (rs.next()) {
                        ub.setId(rs.getString("Id"));
                        ub.setName(rs.getString("name"));
                        ub.setPhoneNumber(rs.getString("PhoneNumber"));
                        ub.setEmail(rs.getString("email"));
                    }
                    return ub;
        });
    }
>>>>>>> 214b4f8724da09cb4f990380c37b7522946c8eed
    
}
