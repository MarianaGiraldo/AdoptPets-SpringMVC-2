/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Models.AdoptBean;
import Models.AdoptBean;
import java.sql.ResultSet;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Mariana
 */
public class AdoptDao {
    private final JdbcTemplate jdbcTemplate;

    public AdoptDao() {
        DBConnection con = new DBConnection();
        this.jdbcTemplate = new JdbcTemplate(con.connect());
    }
    
    
    public List listAdoptions(){
        String sql = "SELECT a.id, u.name user_name, p.name pet_name, a.date FROM " +
                "adoptions a INNER JOIN users u " +
                "ON u.id = a.user_id " +
                "INNER JOIN pets p " +
                "ON p.id = a.pet_id;";
        List adoptions = this.jdbcTemplate.queryForList(sql);
        return adoptions;
    }
    
    public int getCode(){
        String sql = "select max(id)+1 as code from adoptions";
        String codeStr = jdbcTemplate.queryForObject(sql, String.class);
        int code = 1;
        if (codeStr != null) {
            code = Integer.parseInt(codeStr);
        }
        return code;
    }
    
    public void saveAdoption(AdoptBean ab){
        int id = ab.getId();
        String sql;
        AdoptBean adoption = this.getAdoptionById(ab.getId());
        // Check if adoption exists
       if (adoption.getId() != 0) {
            sql = "UPDATE `adoptions` SET user_id = ?, pet_id = ?, date = ? WHERE id = " + id;
            //Setting as available the old pet
            String sql2 = "UPDATE `pets` SET `is_adopted` = '0' WHERE `pets`.`id` = (?);";
            this.jdbcTemplate.update(sql2, adoption.getPet_id());
            
            //Setting as adopted the new pet
            String sql3 = "UPDATE `pets` SET `is_adopted` = '1' WHERE `pets`.`id` = (?);";
            this.jdbcTemplate.update(sql3, ab.getPet_id());
        } else {
            sql = "INSERT INTO adoptions (user_id, pet_id, date) VALUES (?, ?, ?)";
            String sql2 = "UPDATE `pets` SET `is_adopted` = '1' WHERE `pets`.`id` = (?);";
            this.jdbcTemplate.update(sql2, ab.getPet_id());
        }
        this.jdbcTemplate.update(sql, ab.getUser_id(), ab.getPet_id(), ab.getDate());
        
        
    }
    
    public AdoptBean getAdoptionById(int id) {
        AdoptBean ab = new AdoptBean();
        String sql = "SELECT * from adoptions WHERE id = " + id;
        return (AdoptBean) this.jdbcTemplate.query(
                sql, (ResultSet rs) -> {
                    if (rs.next()) {
                        ab.setId(rs.getInt("id"));
                        ab.setDate(rs.getString("date"));
                        ab.setUser_id(rs.getString("user_id"));
                        ab.setPet_id(rs.getString("pet_id"));
                    }
                    return ab;
        });
    }
    
    public void deleteAdoption(int id) {
        try {
            AdoptBean adoption = this.getAdoptionById(id);
            
            String sqlAdopt = "DELETE FROM `adoptions` WHERE `adoptions`.`id` = ?";
            this.jdbcTemplate.update(sqlAdopt, id);
            
            //Setting as available the old pet
            String sql2 = "UPDATE `pets` SET `is_adopted` = '0' WHERE `pets`.`id` = (?);";
            this.jdbcTemplate.update(sql2, adoption.getPet_id());
            
            
        } catch (DataAccessException e) {
            System.err.print(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.print(e.getMessage());
        }
    }
    
}
