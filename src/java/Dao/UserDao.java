/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Models.PetBean;
import Models.UserBean;
import Models.UserBeanValidation;
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
public class UserDao {

    private final UserBeanValidation validate_user;
    private final JdbcTemplate jdbcTemplate;
    private final String DELETE_DIRECTORY = "..\\..\\web\\";

    public UserDao() {
        this.validate_user = new UserBeanValidation();
        DBConnection con = new DBConnection();
        this.jdbcTemplate = new JdbcTemplate(con.connect());
    }

    /***
     * List all users in database
     * @return List users
     */
    public List listUsers() {
        String sql = "SELECT * from users";
        List users = this.jdbcTemplate.queryForList(sql);
        return users;
    }
    
    /***
     * List users with less adoption than limit
     * @return List users
     */
    public List listUsersLimitAdopt() {
        String sql = "SELECT * from users WHERE count_adoptions <= 3";
        List users = this.jdbcTemplate.queryForList(sql);
        return users;
    }

    /***
     * Get user model by its id
     * @param id
     * @return UserBean user
     */
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

    /***
     * Insert or update user on database
     * @param ub
     * @return int
     */
    public int saveUser(UserBean ub) {
        int id = ub.getId();
        String sql;
        // Check if user exists
        if (this.getUserById(id).getDocument() != null) {
            if (ub.getPhoto() == null) {
                sql = "UPDATE `users` SET  document = ?, name = ?, phoneNumber = ?, email = ? WHERE id = " + id;
                return this.jdbcTemplate.update(sql, ub.getDocument(), ub.getName(), ub.getPhoneNumber(), ub.getEmail());
            }
            sql = "UPDATE `users` SET  `photo`= ?, document = ?, name = ?, phoneNumber = ?, email = ?, old_photo = ? WHERE id = " + id;
        } else {
            sql = "INSERT INTO users(photo, document, name, phoneNumber, email, old_photo) VALUES (?, ?, ?, ?, ?, ?)";
        }
        return this.jdbcTemplate.update(sql, ub.getPhoto(), ub.getDocument(), ub.getName(), ub.getPhoneNumber(), ub.getEmail(), ub.getPhoto());
    }

    /***
     * Delete user on database by its id
     * @param id 
     */
    public void deleteUser(int id) {
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

    /***
     * Get last id saved plus 1
     * @return int code
     */
    public int getCode() {
        String sql = "select max(id)+1 as code from users";
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
    public void deleteUserAndImage(int id, String photo, String deletePath) {
        String deleteFile = deletePath + DELETE_DIRECTORY + photo;
        File f = new File(deleteFile);
        try {
            if (!f.delete()) {
                throw new Exception();
            } else {
                this.deleteUser(id);
            }
        } catch (Exception e) {
            System.err.println("Error deleting: " + e.getMessage());
        }
    }

    /**
     * *
     * Function to insert or update User and Photo on database
     *
     * @param items
     * @param list
     * @param uploadPath
     * @param uploadPathBuild
     * @param deletePath
     * @param ub
     * @param result
     * @param mav
     * @return ModelAndView mav
     */
    public ModelAndView saveUserandPhoto(
            List<FileItem> items,
            ArrayList<String> list,
            String uploadPath,
            String uploadPathBuild,
            String deletePath,
            UserBean ub,
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

                int usercode = this.getCode();
                String uniqueName = usercode + list.get(4) + '-' + f;
                String filename = "public/images/users/" + uniqueName;
                File uploadFile = new File(uploadPath, uniqueName);
                File uploadFile2 = new File(uploadPathBuild, uniqueName);
                try {
                    if (uploadPath != null) {
                        //Deletes the old photo
                        this.deleteUpdatedPhoto(ub.getOld_photo(), deletePath);
                    }
                    //Save file
                    fileItem.write(uploadFile);
                    fileItem.write(uploadFile2);
                } catch (Exception e) {
                    System.err.println("Error en file.write: " + e.getMessage());
                }
                ub.setPhoto(filename);
            } else {
                list.add(fileItem.getString());
            }
        }
        try {
            ub = this.setUserFromList(list, ub);
            mav = this.validateUser(ub, result, mav);
        } catch (NumberFormatException e) {
            mav.addObject("user", new UserBean());
            mav.setViewName("Views/jstlform_pet");
        }
        return mav;
    }

    /***
     * Deletes old photo file when updating user
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
     * Update Pet but no photo
     * @param ub
     * @param list
     * @param mav
     * @param result
     * @return ModelAndView mav
     */
    public ModelAndView updateUsernoPhoto(UserBean ub, ArrayList<String> list, ModelAndView mav, BindingResult result) {
        try {
            ub = this.setUserFromList(list, ub);
            mav = this.validateUser(ub, result, mav);
        } catch (NumberFormatException e) {
            mav.addObject("pet", new PetBean());
            mav.setViewName("Views/jstlform_pet");
        }
        return mav;
    }

    /***
     * Pet form validation to save user or redirect to form
     * @param ub
     * @param result
     * @param mav
     * @return 
     */
    public ModelAndView validateUser(UserBean ub, BindingResult result, ModelAndView mav) {
        this.validate_user.validate(ub, result);
        if (result.hasErrors()) {
            mav.addObject("ub", new UserBean());
            mav.setViewName("Views/jstlform_user");
        } else {
            //Insert or Update on users table
            this.saveUser(ub);
            mav.addObject("ub", ub);
            mav.setViewName("Views/jstlview_user");
        }
        return mav;
    }

    /***
     * Set Pet attributes from list
     * @param list
     * @param ub
     * @return UserBean ubs
     */
    public UserBean setUserFromList(ArrayList<String> list, UserBean ub) {
        ub.setName(list.get(1));
        ub.setEmail(list.get(2));
        ub.setPhoneNumber(list.get(3));
        ub.setDocument(list.get(4));

        return ub;
    }

}
