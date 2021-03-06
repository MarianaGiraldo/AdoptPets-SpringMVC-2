/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Models.PetBean;
import Models.PetBeanValidation;
import Models.UserBean;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Mariana
 */
public class PetDao {

    private final JdbcTemplate jdbcTemplate;
    private final PetBeanValidation validate_pet;
    private final String DELETE_DIRECTORY = "..\\..\\web\\";

    public PetDao() {
        DBConnection con = new DBConnection();
        this.jdbcTemplate = new JdbcTemplate(con.connect());
        this.validate_pet = new PetBeanValidation();
    }

    /***
     * List all pets in database
     * @return List pets
     */
    public List listPets() {
        String sql = "select * from pets";
        List pets = this.jdbcTemplate.queryForList(sql);
        return pets;
    }

    /***
     * List available pets in database
     * @return List pets
     */
    public List listAvailablePets() {
        String sql = "select * from pets WHERE is_adopted = 0";
        List pets = this.jdbcTemplate.queryForList(sql);
        return pets;
    }

    /***
     * Get pet model by its id
     * @param id
     * @return PetBean pet
     */
    public PetBean getPetxId(int id) {
        PetBean pb = new PetBean();
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
    
    /***
     * Insert or update pet on database
     * @param pb
     * @return int
     */
    public int savePet(PetBean pb) {
        String sql;
        // Check if pet exists
        if (this.getPetxId(pb.getId()).getId() != 0) {
            //Check if it will update photo
            if (pb.getPhoto() == null) {
                sql = "UPDATE `pets` SET `Pet_type`= ?,`Name`= ?,`Born_year`= ?,`Color`= ?,`Breed`= ? ,`is_adopted`= ?  WHERE id = " + pb.getId();
                return this.jdbcTemplate.update(sql, pb.getPet_type(), pb.getName(), pb.getBorn_year(), pb.getColor(), pb.getBreed(), pb.getIs_adopted());
            }
            sql = "UPDATE `pets` SET `Pet_type`= ?,`Name`= ?,`Born_year`= ?,`Color`= ?,`Breed`= ? ,`is_adopted`= ?, `photo`= ?, `old_photo`= ? WHERE id = " + pb.getId();
        } else {
            sql = "INSERT INTO pets(pet_type, name, Born_Year, color, breed, is_adopted, photo, old_photo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        }
        return this.jdbcTemplate.update(sql, pb.getPet_type(), pb.getName(), pb.getBorn_year(), pb.getColor(), pb.getBreed(), pb.getIs_adopted(), pb.getPhoto(), pb.getPhoto());
    }

    /***
     * Delete pet on database by its id
     * @param id 
     */
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
    
    /***
     * Get last id saved plus 1
     * @return int code
     */
    public int getCode() {
        String sql = "select max(id)+1 as code from pets";
        String codeStr = jdbcTemplate.queryForObject(sql, String.class);
        int code = 1;
        if (codeStr != null) {
            code = Integer.parseInt(codeStr);
        }
        return code;
    }

    /***
     * Deletes image file and pet model on database
     * @param id
     * @param photo
     * @param deletePath 
     */
    public void deletePetAndImage(int id, String photo, String deletePath) {
        String deleteFile = deletePath + DELETE_DIRECTORY + photo;
        File f = new File(deleteFile);
        try {
            if (!f.delete()) {
                throw new Exception();
            } else {
                this.deletePet(id);
            }
        } catch (Exception e) {
            System.err.println("Error deleting: " + e.getMessage());
        }
    }

    /***
     * Insert or Update user and photo on database
     * @param items
     * @param list
     * @param uploadPath
     * @param uploadPathBuild
     * @param deletePath
     * @param pb
     * @param result
     * @param mav
     * @return ModelAndView mav
     */
    public ModelAndView savePetandPhoto(
            List<FileItem> items,
            ArrayList<String> list,
            String uploadPath,
            String uploadPathBuild,
            String deletePath,
            PetBean pb,
            BindingResult result,
            ModelAndView mav
    ) {
        for (FileItem item : items) {
            //Create a fileItem var to get the form values
            FileItem fileItem = (FileItem) item;

            //Check if is a file type
            if (!fileItem.isFormField()) {
                //Get the file name
                String f = new File(fileItem.getName()).getName();

                int petcode = this.getCode();
                String uniqueName = petcode + list.get(1) + list.get(2) + '-' + f;
                String filename = "public/images/pets/" + uniqueName;
                File uploadFile = new File(uploadPath, uniqueName);
                File uploadFile2 = new File(uploadPathBuild, uniqueName);
                try {
                    if (uploadPath != null) {
                        //Deletes the old photo
                        this.deleteUpdatedPhoto(pb.getOld_photo(), deletePath);
                    }
                    //Save file
                    fileItem.write(uploadFile);
                    fileItem.write(uploadFile2);
                } catch (Exception e) {
                    System.err.println("Error en file.write: " + e.getMessage());
                }
                pb.setPhoto(filename);
            } else {
                list.add(fileItem.getString());
            }
        }
        try {
            pb = this.setPetFromList(list, pb);
            mav = this.validatePet(pb, result, mav);
        } catch (NumberFormatException e) {
            mav.addObject("pet", new PetBean());
            mav.setViewName("Views/jstlform_pet");
        }
        return mav;
    }

    /***
     * Update Pet but no photo
     * @param pb
     * @param list
     * @param mav
     * @param result
     * @return ModelAndView mav
     */
    public ModelAndView updatePetnoPhoto(
            PetBean pb,
            ArrayList<String> list,
            ModelAndView mav,
            BindingResult result
    ) {
        try {
            pb = this.setPetFromList(list, pb);
            mav = this.validatePet(pb, result, mav);
        } catch (NumberFormatException e) {
            mav.addObject("pet", new PetBean());
            mav.setViewName("Views/jstlform_pet");
        }
        return mav;
    }

    /***
     * Deletes old photo file when updating pet
     * @param photo
     * @param deletePath 
     */
    public void deleteUpdatedPhoto(String photo, String deletePath) {
        String deleteFile = deletePath + DELETE_DIRECTORY + photo;
        File f = new File(deleteFile);
        try {
            if (!f.delete()) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.err.println("Error deleting: " + e.getMessage());
        }
    }

    /***
     * Pet form validation to save pet or redirect to form
     * @param pb
     * @param result
     * @param mav
     * @return ModelAndView mav
     */
    public ModelAndView validatePet(PetBean pb, BindingResult result, ModelAndView mav) {
        this.validate_pet.validate(pb, result);
        if (result.hasErrors()) {
            mav.addObject("pet", new PetBean());
            mav.setViewName("Views/jstlform_pet");
        } else {
            //Insert or Update on pets table
            this.savePet(pb);
            mav.addObject("pet", pb);
            mav.setViewName("Views/jstlview_pet");
        }
        return mav;
    }

    /***
     * Set Pet attributes from list
     * @param list
     * @param pb
     * @return PetBean pb
     */
    public PetBean setPetFromList(ArrayList<String> list, PetBean pb) {
        pb.setName(list.get(1));
        pb.setBorn_year(Integer.parseInt(list.get(2)));
        pb.setColor(list.get(3));
        pb.setBreed(list.get(4));
        pb.setPet_type(list.get(5));
        pb.setIs_adopted(Boolean.parseBoolean(list.get(6)));

        return pb;
    }

}
