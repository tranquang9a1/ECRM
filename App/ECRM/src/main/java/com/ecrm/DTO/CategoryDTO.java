package com.ecrm.DTO;

/**
 * Created by QuangTV on 7/30/2015.
 */
public class CategoryDTO {

    private String name;
    private String imageUrl;

    public CategoryDTO(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public CategoryDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
