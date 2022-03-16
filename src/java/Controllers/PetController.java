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
    //Uploading Images vars
    private static final String UPLOAD_DIRECTORY = "..\\..\\web\\public\\images\\pets";
    private static final String UPLOAD_DIRECTORYBUILD = "public\\images\\pets";
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; //3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; //40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; //50MB

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
     * Form Post and Validation
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
                    String f = new  File(fileItem.getName()).getName();
                    
                    int petcode = petDao.getCode();
                    
                    String filename = "public/images/pets/" + petcode + f ;
                    System.out.println("Filename: " + filename);
                    File uploadFile = new File(uploadPath, petcode + f);
                    File uploadFile2 = new File(uploadPathBuild, petcode + f);
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
        //this.petDao.deletePet(id);
        String deletePath = request.getServletContext().getRealPath("") + File.separator;
        String photo = this.petDao.getPetxId(id).getPhoto();
        this.petDao.deletePetAndImage(id, photo, deletePath);
        mav.setViewName("redirect:/listpets.htm");
        return mav;
    }

}
