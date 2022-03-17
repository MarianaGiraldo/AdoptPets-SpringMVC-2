/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import javax.ejb.Stateless;

/**
 *
 * @author Mariana
 */
@Stateless
public class UserBean {
    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private String document;
    private String photo;
    private String old_photo;
    

    public UserBean() {
    }

    public UserBean(String name, String phoneNumber, String email, String id) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.document = id;
    }

    public UserBean(String name, String phoneNumber, String email, String document, String photo, String old_photo) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.document = document;
        this.photo = photo;
        this.old_photo = old_photo;
    }

    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * @return the document
     */
    public String getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @return the old_photo
     */
    public String getOld_photo() {
        return old_photo;
    }

    /**
     * @param old_photo the old_photo to set
     */
    public void setOld_photo(String old_photo) {
        this.old_photo = old_photo;
    }

    
}
