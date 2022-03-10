/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Models.PetBean;
import Models.UserBean;
import java.sql.ResultSet;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Mariana
 */
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDao() {
        DBConnection con = new DBConnection();
        this.jdbcTemplate = new JdbcTemplate(con.connect());
    }
    
    
    public List listUsers(){
        String sql = "SELECT * from users";
        List users = this.jdbcTemplate.queryForList(sql);
        return users;
    }
    
    public UserBean getUserById(int id) {
        UserBean ub = new UserBean();
        String sql = "SELECT * from users WHERE id = " + id;
        return (UserBean) this.jdbcTemplate.query(
                sql, (ResultSet rs) -> {
                    if (rs.next()) {
                        ub.setId(rs.getInt("Id"));
                        ub.setDocument(rs.getString("Document"));
                        ub.setEmail(rs.getString("Email"));
                        ub.setName(rs.getString("Name"));
                        ub.setPhoneNumber(rs.getString("PhoneNumber"));
                        ub.setPhoto(rs.getString("Photo"));
                    }
                    return ub;
        });
    }
    
    public void saveUser(UserBean ub){
        int id = ub.getId();
        String sql;
        // Check if user exists
        if (this.getUserById(id).getDocument() != null) {
            sql = "UPDATE `users` SET  `photo`= ?, document = ?, name = ?, phoneNumber = ?, email = ? WHERE id = " + id;
        } else {
            sql = "INSERT INTO users(photo, document, name, phoneNumber, email, photo) VALUES (?, ?, ?, ?, ?)";
        }
        this.jdbcTemplate.update(sql, ub.getPhoto(), ub.getDocument(), ub.getName(), ub.getPhoneNumber(), ub.getEmail());
    }
    
    public void deleteUser(int id){
        try {
            String sqlAdopt = "DELETE FROM `adoptions` WHERE `adoptions`.`user_id` = ?";
            this.jdbcTemplate.update(sqlAdopt, id);
            String sql = "DELETE from users WHERE id = ?";
            this.jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.print(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.print(e.getMessage());
        }
    }
    
}
