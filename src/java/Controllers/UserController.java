/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Dao.UserDao;
import Models.UserBean;
import Models.UserBeanValidation;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
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
     //Uploading Images vars
    private static final String UPLOAD_DIRECTORY = "..\\..\\web\\public\\images\\users";
    private static final String UPLOAD_DIRECTORYBUILD = "public\\images\\users";
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; //3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; //40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; //50MB


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
        Boolean update = false;
        if (request.getParameter("id") != null) {
            //Update form
            String old_photo = request.getParameter("old_photo");
            int id = Integer.parseInt(request.getParameter("id"));
            UserBean user = this.userDao.getUserById(id);
            user.setOld_photo(old_photo);
            System.out.println(user.getOld_photo());
            mav.addObject("user", user);
            update = true;
        } else {
            //Insert form
            mav.addObject("user", new UserBean());
        }
        mav.addObject("update", update);
        return mav;
    }

    /***
     * Insert or update form
     * @param ub
     * @param result
     * @param status
     * @param request
     * @return 
     */
    @RequestMapping(value = "form_user.htm", method = RequestMethod.POST)
    public ModelAndView saveUserForm(
            @ModelAttribute("user") UserBean ub,
            BindingResult result,
            SessionStatus status,
            HttpServletRequest request
    ) {
        ModelAndView mav = new ModelAndView();
        /*  Manage Images upload */
        //Check is form is multipart
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        //Create a List to get the vector
        ArrayList<String> list = new ArrayList<>();

        if (isMultipart) {
            //Create a type file var
            DiskFileItemFactory file = new DiskFileItemFactory();
            //Set maximum memory allowed
            file.setSizeThreshold(MEMORY_THRESHOLD);
            //Set maximum request value
            file.setRepository(new File(System.getProperty("java.io.tmpdir")));
            //Put the file as parameter to a fileUpload
            ServletFileUpload fileUpload = new ServletFileUpload(file);

            //Set Max file size limit
            fileUpload.setFileSizeMax(MAX_FILE_SIZE);
            //Set max request size
            fileUpload.setSizeMax(MAX_REQUEST_SIZE);
            //Construye una ruta temporal para almacenar archivos cargados
            String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uploadPathBuild = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORYBUILD;
            File uploadDirBuild = new File(uploadPathBuild);

            if (!uploadDirBuild.exists()) {
                uploadDirBuild.mkdir();
            }
            //Creates a temporal path to delete files
            String deletePath = request.getServletContext().getRealPath("") + File.separator;
            
            //Create a list with the form values
            List<FileItem> items = null;
            try {
                items = fileUpload.parseRequest(request);
                for (FileItem item : items) {
                    FileItem fileItem = (FileItem) item;
                    list.add(fileItem.getString());
                }
                //For  to add fields to list
            } catch (FileUploadException e) {
                System.out.println("Error getting request items: " + e.getMessage());
            }
            System.out.println("List: "+ list);
            //Checks if form action is update
            if(!Boolean.parseBoolean(list.get(0))){
                //Insert new pet and image
                deletePath = null;
                mav = this.userDao.saveUserandPhoto(items, list, uploadPath, uploadPathBuild, deletePath, ub, result, mav);
            } else{
                //Update pet
                //Checks if photo will be updated
                if(list.get(5).isEmpty() || list.get(5).equals("") || list.get(5) == null){
                    System.out.println("Updates no photo");
                    mav = this.userDao.updateUsernoPhoto(ub, list, mav, result);
                }else{
                    System.out.println("Update with photo");
                   mav = this.userDao.saveUserandPhoto(items, list, uploadPath, uploadPathBuild, deletePath, ub, result, mav);
                }
            }
        }
        return mav;
    }

    @RequestMapping(value = "deleteuser.htm", method = RequestMethod.GET)
    public ModelAndView deleteUser(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        int id = Integer.parseInt(request.getParameter("id"));
        String deletePath = request.getServletContext().getRealPath("") + File.separator;
        String photo = this.userDao.getUserById(id).getPhoto();
        this.userDao.deleteUserAndImage(id, photo, deletePath);
        mav.setViewName("redirect:/listusers.htm");
        return mav;
    }

}
