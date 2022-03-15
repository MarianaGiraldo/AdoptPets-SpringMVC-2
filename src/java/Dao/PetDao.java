/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Models.PetBean;
import java.sql.ResultSet;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Mariana
 */
public class PetDao {
    private final JdbcTemplate jdbcTemplate;

    public PetDao() {
        DBConnection con = new DBConnection();
        this.jdbcTemplate = new JdbcTemplate(con.connect());
    }
    
    public List listPets(){
        String sql = "select * from pets";
        List pets = this.jdbcTemplate.queryForList(sql);
        return pets;
    }
    
    public List listAvailablePets(){
        String sql = "select * from pets WHERE is_adopted = 0";
        List pets = this.jdbcTemplate.queryForList(sql);
        return pets;
    }
    
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
                        pb.setPhoto(rs.getString("Photo"));
                    }
                    return pb;
        });
    }
    
    public void savePet(PetBean pb){
        String sql;
        // Check if pet exists
        if (this.getPetxId(pb.getId()).getId() != 0) {
            sql = "UPDATE `pets` SET `Pet_type`= ?,`Name`= ?,`Born_year`= ?,`Color`= ?,`Breed`= ? ,`is_adopted`= ?, `photo`= ? WHERE id = " + pb.getId();
        } else {
            sql = "INSERT INTO pets(pet_type, name, Born_Year, color, breed, is_adopted, photo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        }
        this.jdbcTemplate.update(sql, pb.getPet_type(), pb.getName(), pb.getBorn_year(), pb.getColor(), pb.getBreed(), pb.getIs_adopted(), pb.getPhoto());
    }
    
    public void deletePet(int id) {
        try {
            String sqlAdopt = "DELETE FROM `adoptions` WHERE `adoptions`.`pet_id` = ?";
            this.jdbcTemplate.update(sqlAdopt, id);
            String sql = "DELETE from pets WHERE id = ?";
            this.jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.print(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.print(e.getMessage());
        }
    }

    public int getCode(){
        String sql = "select max(id)+1 as code from pets";
        String codeStr = jdbcTemplate.queryForObject(sql, String.class);
        int code = 1;
        if (codeStr != null) {
            code = Integer.parseInt(codeStr);
        }
        return code;
    }
    
}
