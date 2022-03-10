/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Dao.UserDao;
import Models.PetBean;
import Models.UserBean;
import Models.UserBeanValidation;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
    public ModelAndView listUsers() {
        ModelAndView mav = new ModelAndView();
        List users = this.userDao.listUsers();
        mav.addObject("users", users);
        mav.setViewName("Views/listusers");
        return mav;
    }

    //User Form Method: GET
    @RequestMapping(value = "form_user.htm", method = RequestMethod.GET)
    public ModelAndView getUserForm(HttpServletRequest request) {
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

    /**
     * *
     * Form Validation
     *
     * @param ub
     * @param result
     * @param status
     * @param request
     * @return ModelAndView *
     */
    @RequestMapping(value = "form_user.htm", method = RequestMethod.POST)
    public ModelAndView valPostUserForm(
            @ModelAttribute("user") UserBean ub,
            BindingResult result,
            SessionStatus status,
            HttpServletRequest request
    ) {
        ModelAndView mav = new ModelAndView();
        /*  Manage Images upload */
        //Get path
        String uploadFilePath = request.getSession().getServletContext().getRealPath("../../web/public/images/users");
        String uploadFilePath2 = request.getSession().getServletContext().getRealPath("public/images/users");

        //Check is form is multipart
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        //Create a List to get the vector
        ArrayList<String> list = new ArrayList<>();

        if (isMultipart) {
            //Create a type file var
            FileItemFactory file = new DiskFileItemFactory();
            //Put the file as parameter to a fileUpload
            ServletFileUpload fileUpload = new ServletFileUpload(file);

            //Create a list with the form values
            List<FileItem> items = null;
            try {
                items = fileUpload.parseRequest(request);
            } catch (FileUploadException e) {
                System.out.println("Error getting request items: " + e.getMessage());
            }
            for (FileItem item : items) {
                //Create a fileItem var to get the form values
                FileItem fileItem = (FileItem) item;

                //Check if is a file type
                if (!fileItem.isFormField()) {
                    //Get the file name
                    File f = new File("public/images/users/" + fileItem.getName());
                    String filename = "public/images/users/" + f.getName();
                    System.out.println("Filename: " + filename);
                    File uploadFile = new File(uploadFilePath, f.getName());
                    File uploadFile2 = new File(uploadFilePath2, f.getName());
                    try {
                        //Save file
                        fileItem.write(uploadFile);
                        fileItem.write(uploadFile2);

                    } catch (Exception e) {
                        System.out.println("Error en file.write: " + e.getMessage());
                    }
                    ub.setPhoto(filename);
                    System.out.println("Photo: " + ub.getPhoto());
                } else {
                    list.add(fileItem.getString());
                }
            }
            System.out.println("List: " + list);
            try {
                ub.setName(list.get(0));
                ub.setEmail(list.get(1));
                ub.setPhoneNumber(list.get(2));
                ub.setDocument(list.get(3));

                this.validate_user.validate(ub, result);
                if (result.hasErrors()) {
                    mav.addObject("ub", new UserBean());
                    mav.setViewName("Views/jstlform_user");
                } else {
                    //Insert or Update on users table
                    System.out.println("Saving user");
                    this.userDao.saveUser(ub);
                    mav.addObject("ub", ub);
                    mav.setViewName("Views/jstlview_user");
                }
            } catch (NumberFormatException e) {
                mav.addObject("ub", new UserBean());
                mav.setViewName("Views/jstlform_user");
            }

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
