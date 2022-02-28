/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controllers;

import Dao.DBConnection;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Mariana
 */
@Controller
public class UserController {
    private final JdbcTemplate jdbcTemplate;

    public UserController() {
        DBConnection con = new DBConnection();
        this.jdbcTemplate = new JdbcTemplate(con.connect());
    }
    
    
    @RequestMapping("listusers.htm")
    public ModelAndView listUsers(){
        ModelAndView mav = new ModelAndView();
        
        String sql = "SELECT * from users";
        List users = this.jdbcTemplate.queryForList(sql);
        mav.addObject("users", users);
        mav.setViewName("Views/listusers");
        return mav;
    }
    
}
