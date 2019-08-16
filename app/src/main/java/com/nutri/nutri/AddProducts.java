package com.nutri.nutri;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nutri.nutri.ob_box.ObjectBox;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import io.objectbox.Box;

public class AddProducts extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 677;
    private static final int REQUEST_CODE = 433;
    ImageView addImage,photo;
    EditText addName,addDescription;
    Button addProduct;
    private Uri photoUri;
    private File mPhotoFile;
    private Product mProduct;
    private Box<Product> mProductBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        mProductBox = ObjectBox.get().boxFor(Product.class);
        addImage = findViewById(R.id.addImage);
        photo = findViewById(R.id.photo);
        addName = findViewById(R.id.addName);
        addDescription = findViewById(R.id.addDescription);
        addProduct = findViewById(R.id. addProduct);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGallery();
            }
        });
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });
    }

    private void addToList() {
        String name = addName.getText().toString();
        String describe = addDescription.getText().toString();
        String picturePath = photoUri.toString();
        mProduct = new Product(name,describe,picturePath);
        mProductBox.put(this.mProduct);
        finish();
    }

    private void getGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose a photo"),GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){
            return;
        }
        else if (requestCode == GALLERY_REQUEST_CODE){
            Uri photopath = data.getData();
            photoUri = photopath;
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),photopath);
                updatePhotoView();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    private void updatePhotoView() {
        if (photoUri!=null){
            Glide.with(this).load(photoUri)
                    .into(addImage);
        }
    }
    public void savePhotoToFilePathAndRetrieve(){
        mPhotoFile = getPhotoFile();
    }

    private File getPhotoFile() {
        File filesDir = getFilesDir();
        return new File(filesDir,getPhotoFilename());
    }

    private String getPhotoFilename() {
        return "IMG_" + new Random().nextDouble() +".jpg";
    }
}
