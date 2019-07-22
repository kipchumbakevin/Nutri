package com.nutri.nutri;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by KIPCHU on 02/07/2019.
 */
@Entity public class Product {
@Id long id;

     String name;
     int image;


     public String getName(){
         return name;
     }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}