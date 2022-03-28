/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Dao.PetDao;
import Models.PetBean;
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
public class PetController {

    private final PetDao petDao;
    //Uploading Images vars
    private static final String UPLOAD_DIRECTORY = "..\\..\\web\\public\\images\\pets";
    private static final String UPLOAD_DIRECTORYBUILD = "public\\images\\pets";
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; //3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; //40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; //50MB

    /**
     * *
     * PetController Constructor
     */
    public PetController() {
        this.petDao = new PetDao();
    }

    /**
     * *
     * Method get to list Pets
     *
     * @return ModelAndView mav
     */
    @RequestMapping(value = "listpets.htm", method = RequestMethod.GET)
    public ModelAndView listPets() {
        ModelAndView mav = new ModelAndView();
        List pets;
        pets = petDao.listPets();
        mav.addObject("pets", pets);
        mav.setViewName("Views/list_pets");

        return mav;
    }

    /**
     * *
     * Get pet form to insert and update
     *
     * @param request
     * @return ModelAndView mav
     */
    @RequestMapping(value = "form_pet.htm", method = RequestMethod.GET)
    public ModelAndView getPetForm(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("Views/jstlform_pet");
        Boolean update = false;
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            String old_photo = request.getParameter("old_photo");
            PetBean pet = this.petDao.getPetxId(id);
            pet.setOld_photo(old_photo);
            mav.addObject("pet", pet);
            update = true;
        } else {
            mav.addObject("pet", new PetBean());
        }
        mav.addObject("update", update);
        return mav;
    }

    /**
     * *
     * Form Post and Validation
     *
     * @param pb
     * @param result
     * @param status
     * @param request
     * @return ModelAndView mav
     */
    @RequestMapping(value = "form_pet.htm", method = RequestMethod.POST)
    public ModelAndView savePetForm(
            @ModelAttribute("pet") PetBean pb,
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
            //Creates a temporal path to save files
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
            System.out.println("List: " + list);
            //Checks if form action is update
            if (!Boolean.parseBoolean(list.get(0))) {
                //Insert new pet and image
                deletePath = null;
                mav = this.petDao.savePetandPhoto(items, list, uploadPath, uploadPathBuild, deletePath, pb, result, mav);
            } else {
                //Update pet
                //Checks if photo will be updated
                if (list.get(7).isEmpty() || list.get(7).equals("") || list.get(7) == null) {
                    mav = this.petDao.updatePetnoPhoto(pb, list, mav, result);
                } else {
                    mav = this.petDao.savePetandPhoto(items, list, uploadPath, uploadPathBuild, deletePath, pb, result, mav);
                }
            }
        }
        return mav;
    }

    /**
     * *
     * Method to delete pet and image
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "deletepet.htm", method = RequestMethod.GET)
    public ModelAndView deletePet(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        int id = Integer.parseInt(request.getParameter("id"));
        String deletePath = request.getServletContext().getRealPath("") + File.separator;
        String photo = this.petDao.getPetxId(id).getPhoto();
        this.petDao.deletePetAndImage(id, photo, deletePath);
        mav.setViewName("redirect:/listpets.htm");
        return mav;
    }

}
