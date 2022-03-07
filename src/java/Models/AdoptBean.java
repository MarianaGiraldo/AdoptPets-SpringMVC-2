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
public class AdoptBean {
    private int id;
    private String user_id;
    private String pet_id;
    private String date;

    public AdoptBean() {
    }

    public AdoptBean(String user_id, String pet_id, String date) {
        this.user_id = user_id;
        this.pet_id = pet_id;
        this.date = date;
    }

    

    /**
     * @return the user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the pet_id
     */
    public String getPet_id() {
        return pet_id;
    }

    /**
     * @param pet_id the pet_id to set
     */
    public void setPet_id(String pet_id) {
        this.pet_id = pet_id;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
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
}
