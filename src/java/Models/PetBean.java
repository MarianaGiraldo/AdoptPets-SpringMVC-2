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
public class PetBean {
    private int id;
    private String pet_type;
    private String name;
    private int born_year;
    private String color;
    private String breed;
    private Boolean is_adopted;
    private String photo;

    public PetBean() {
    }

    public PetBean(String pet_type, String name, int born_year, String color, String breed, Boolean is_adopted) {
        this.pet_type = pet_type;
        this.name = name;
        this.born_year = born_year;
        this.color = color;
        this.breed = breed;
        this.is_adopted = is_adopted;
    }

    public PetBean(String pet_type, String name, int born_year, String color, String breed, Boolean is_adopted, String photo) {
        this.pet_type = pet_type;
        this.name = name;
        this.born_year = born_year;
        this.color = color;
        this.breed = breed;
        this.is_adopted = is_adopted;
        this.photo = photo;
    }
    
    
    
    /**
     * @return the pet_type
     */
    public String getPet_type() {
        return pet_type;
    }

    /**
     * @param pet_type the pet_type to set
     */
    public void setPet_type(String pet_type) {
        this.pet_type = pet_type;
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
     * @return the born_year
     */
    public int getBorn_year() {
        return born_year;
    }

    /**
     * @param born_year the born_year to set
     */
    public void setBorn_year(int born_year) {
        this.born_year = born_year;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the breed
     */
    public String getBreed() {
        return breed;
    }

    /**
     * @param breed the breed to set
     */
    public void setBreed(String breed) {
        this.breed = breed;
    }

    /**
     * @return the is_adopted
     */
    public Boolean getIs_adopted() {
        return is_adopted;
    }

    /**
     * @param is_adopted the is_adopted to set
     */
    public void setIs_adopted(Boolean is_adopted) {
        this.is_adopted = is_adopted;
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
    
    
    
}
