/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Dao.PetDao;
import Models.PetBean;
import Models.PetBeanValidation;
<<<<<<< HEAD
import java.util.List;
import javax.servlet.http.HttpServletRequest;
=======
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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
public class PetController {
<<<<<<< HEAD
    private final PetBeanValidation validate_pet;
    private final PetDao petDao;

    public PetController() {
        this.validate_pet = new PetBeanValidation();
        this.petDao = new PetDao();
=======

    private final JdbcTemplate jdbcTemplate;
    private final PetBeanValidation validate_pet;

    public PetController() {
        DBConnection con = new DBConnection();
        this.jdbcTemplate = new JdbcTemplate(con.connect());
        this.validate_pet = new PetBeanValidation();
>>>>>>> 214b4f8724da09cb4f990380c37b7522946c8eed
    }

    @RequestMapping(value = "listpets.htm", method = RequestMethod.GET)
    public ModelAndView listPets() {
        ModelAndView mav = new ModelAndView();
<<<<<<< HEAD
        List pets;
        pets = petDao.listPets();
        mav.addObject("pets", pets);
        mav.setViewName("Views/list_pets");
        
=======
        try {
            String sql = "SELECT * from pets";
            List pets;
            pets = this.jdbcTemplate.queryForList(sql);
            mav.addObject("pets", pets);
            mav.setViewName("Views/list_pets");
        } catch (DataAccessException e) {
            System.err.print(e.getMessage());
        }
>>>>>>> 214b4f8724da09cb4f990380c37b7522946c8eed
        return mav;
    }

    @RequestMapping(value = "form_pet.htm", method = RequestMethod.GET)
    public ModelAndView getPetForm(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("Views/jstlform_pet");
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
<<<<<<< HEAD
            PetBean pet = this.petDao.getPetxId(id);
=======
            PetBean pet = getPetxId(id);
>>>>>>> 214b4f8724da09cb4f990380c37b7522946c8eed
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
<<<<<<< HEAD
     * @return ModelAndView
=======
     * @return ModelAndView *
>>>>>>> 214b4f8724da09cb4f990380c37b7522946c8eed
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
<<<<<<< HEAD
            //Insert or Update on pets table
            this.petDao.savePet(pb);
=======
            String sql;
            // Check if pet exists
            if (getPetxId(pb.getId()) != null) {
                sql = "UPDATE `pets` SET `Pet_type`= ?,`Name`= ?,`Born_year`= ?,`Color`= ?,`Breed`= ? ,`is_adopted`= ? WHERE id = " + pb.getId();
                System.out.println(sql);
            } else {
                sql = "INSERT INTO pets(pet_type, name, Born_Year, color, breed, is_adopted) VALUES (?, ?, ?, ?, ?, ?)";
            }
            this.jdbcTemplate.update(sql, pb.getPet_type(), pb.getName(), pb.getBorn_year(), pb.getColor(), pb.getBreed(), pb.getIs_adopted());

>>>>>>> 214b4f8724da09cb4f990380c37b7522946c8eed
            mav.addObject("pet", pb);
            mav.setViewName("Views/jstlview_pet");
        }
        return mav;
    }
    
    @RequestMapping(value = "deletepet.htm", method = RequestMethod.GET)
    public ModelAndView deletePet(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
<<<<<<< HEAD
        int id = Integer.parseInt(request.getParameter("id"));
        this.petDao.deletePet(id);
=======
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String sqlAdopt = "DELETE FROM `user_pet` WHERE `user_pet`.`pet_id` = ?";
            this.jdbcTemplate.update(sqlAdopt, id);
            String sql = "DELETE from pets WHERE id = ?";
            this.jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.print(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.print(e.getMessage());
        }
>>>>>>> 214b4f8724da09cb4f990380c37b7522946c8eed
        mav.setViewName("redirect:/listpets.htm");
        return mav;
    }

<<<<<<< HEAD
    
=======
    public PetBean getPetxId(int id) {
        PetBean pb = new PetBean();
        System.out.println("Pet id: " + id);
        String sql = "SELECT * from pets WHERE id = " + id;
        return (PetBean) this.jdbcTemplate.query(
                sql, (ResultSet rs) -> {
                    if (rs.next()) {
                        pb.setId(rs.getInt("Id"));
                        pb.setPet_type(rs.getString("pet_type"));
                        pb.setName(rs.getString("name"));
                        pb.setBorn_year(rs.getInt("born_year"));
                        pb.setColor(rs.getString("Color"));
                        pb.setBreed(rs.getString("Breed"));
                        pb.setPet_type(rs.getString("Pet_type"));
                        pb.setIs_adopted(rs.getBoolean("Is_adopted"));
                    }
                    return pb;
        });
    }
>>>>>>> 214b4f8724da09cb4f990380c37b7522946c8eed
}
