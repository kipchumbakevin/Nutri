package com.nutri.nutri;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by KIPCHU on 02/07/2019.
 */
@Entity public class Product {
@Id long id;

     String name,describe,imageUri;

    public Product(String name, String describe, String imageUri) {
        this.name = name;
        this.describe = describe;
        this.imageUri = imageUri;
    }

    public Product() {
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getName(){
         return name;
     }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return imageUri;
    }

    public void setImage(String image) {
        this.imageUri = image;
    }
}