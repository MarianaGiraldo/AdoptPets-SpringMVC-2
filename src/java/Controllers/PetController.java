/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Dao.PetDao;
import Models.PetBean;
import Models.PetBeanValidation;
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
public class PetController {

    private final PetBeanValidation validate_pet;
    private final PetDao petDao;

    public PetController() {
        this.validate_pet = new PetBeanValidation();
        this.petDao = new PetDao();
    }

    @RequestMapping(value = "listpets.htm", method = RequestMethod.GET)
    public ModelAndView listPets() {
        ModelAndView mav = new ModelAndView();
        List pets;
        pets = petDao.listPets();
        mav.addObject("pets", pets);
        mav.setViewName("Views/list_pets");

        return mav;
    }

    @RequestMapping(value = "form_pet.htm", method = RequestMethod.GET)
    public ModelAndView getPetForm(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("Views/jstlform_pet");
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            PetBean pet = this.petDao.getPetxId(id);
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
     * @param request
     * @return ModelAndView
     */
    @RequestMapping(value = "form_pet.htm", method = RequestMethod.POST)
    public ModelAndView valPostPetForm(
            @ModelAttribute("pet") PetBean pb,
            BindingResult result,
            SessionStatus status,
            HttpServletRequest request
    ) {
        ModelAndView mav = new ModelAndView();
        /*  Manage Images upload */
        //Get path
        String uploadFilePath = request.getSession().getServletContext().getRealPath("../../web/public/images/pets");
        String uploadFilePath2 = request.getSession().getServletContext().getRealPath("public/images/pets");
        
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
                    File f = new File("public/images/pets/" + fileItem.getName());
                    String filename = "public/images/pets/" + f.getName();
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
                    pb.setPhoto(filename);
                    System.out.println("Photo: " + pb.getPhoto());
                } else {
                    list.add(fileItem.getString());
                }
            }
            System.out.println("List: " + list);
            try {
                pb.setName(list.get(0));
                pb.setBorn_year(Integer.parseInt(list.get(1)));
                pb.setColor(list.get(2));
                pb.setBreed(list.get(3));
                pb.setPet_type(list.get(4));
                pb.setIs_adopted(Boolean.parseBoolean(list.get(5)));

                this.validate_pet.validate(pb, result);
                if (result.hasErrors()) {
                    mav.addObject("pet", new PetBean());
                    mav.setViewName("Views/jstlform_pet");
                } else {
                    //Insert or Update on pets table
                    this.petDao.savePet(pb);
                    mav.addObject("pet", pb);
                    mav.setViewName("Views/jstlview_pet");
                }
            } catch (NumberFormatException e) {
                mav.addObject("pet", new PetBean());
                mav.setViewName("Views/jstlform_pet");
            }

        }
        return mav;
    }

    @RequestMapping(value = "deletepet.htm", method = RequestMethod.GET)
    public ModelAndView deletePet(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        int id = Integer.parseInt(request.getParameter("id"));
        this.petDao.deletePet(id);
        mav.setViewName("redirect:/listpets.htm");
        return mav;
    }

}
