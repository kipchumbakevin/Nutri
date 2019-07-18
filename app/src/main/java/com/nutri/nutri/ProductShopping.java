package com.nutri.nutri;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Map;

public class ProductShopping extends AppCompatActivity {
    private static final int REQUEST_CODE = 433;
    public static final String MILK_QUANTITY = "com.nutri.nutri.milk_quantity";
    public static final String SUKUMA_QUANTITY = "com.nutri.nutri.sukuma_quantity";
    public static final String EGGS_QUANTITY = "com.nutri.nutri.eggs_quantity";
    public static final String KUKU_QUANTITY = "com.nutri.nutri.kuku_quantity";
    public int mcurrentPosition;
    int initial,newNum;
    Button maziwa,mail;
    EditText milk,sukuma,eggs,kuku;
    TextView cartA;
    ImageView add, sukumaw,eggsm,kukuk,cart,camera;
    int milkQuantity,sukumaQuantity, eggsQuantity,chickenQuantity,cart_quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_shopping);
        maziwa=findViewById(R.id.button3);
        cartA= findViewById(R.id.cartAdd);
        initial= 0;
        kukuk= findViewById(R.id.imageKuku);
        milk= findViewById(R.id.textView);
        sukuma= findViewById(R.id.textSukuma);
        eggs= findViewById(R.id.textEggs);
        kuku= findViewById(R.id.textKuku);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cart=  findViewById(R.id.addCart);
        mail=findViewById(R.id.mail);
        camera=findViewById(R.id.photo);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,REQUEST_CODE);
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
        kukuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input=kuku.getText().toString();
                if (input.isEmpty()){
                    kuku.setError("An empty field is not added to Cart");
                    return;
                }
                if (input.equals("0")) {
                    kuku.setError("An empty field is not added to Cart");
                    return;
                }
                else{
                    chickenQuantity=Integer.parseInt(input);
                    newNum= ++initial;
                    cartA.setText(""+newNum);
                    kuku.getText().clear();
                    Toast.makeText(ProductShopping.this,"Added to Cart", Toast.LENGTH_SHORT).show();
                }

            }
        });
        eggsm= findViewById(R.id.imageEggs);
        eggsm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input=eggs.getText().toString();
                if (input.isEmpty()){
                    eggs.setError("An empty field is not added to Cart");
                    return;
                }
                if (input.equals("0")) {
                    eggs.setError("An empty field is not added to Cart");
                    return;
                }
                else {
                    eggsQuantity=Integer.parseInt(input);
                    newNum= ++initial;
                    cartA.setText(""+newNum);
                    eggs.getText().clear();
                    Toast.makeText(ProductShopping.this,"Added to Cart", Toast.LENGTH_SHORT).show();
                }

            }
        });
        sukumaw= findViewById(R.id.imageSukuma);
        sukumaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input=sukuma.getText().toString();
                if (input.isEmpty()){
                    sukuma.setError("An empty field is not added to Cart");
                    return;
                }
                if (input.equals("0")) {
                    sukuma.setError("An empty field is not added to Cart");
                }
                else {
                    if (Integer.parseInt(input) % 5 == 0) {
                        sukumaQuantity=Integer.parseInt(input);
                        newNum = ++initial;
                        cartA.setText("" + newNum);
                        sukuma.getText().clear();
                        Toast.makeText(ProductShopping.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                    } else
                        sukuma.setError("Sukumawiki are packaged in bundles of Ksh.5 each");
                }

            }
        });
        add= findViewById(R.id.imageView);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input=milk.getText().toString();
                if (input.isEmpty()) {
                    milk.setError("An empty field is not added to Cart");
                    return;
                }
                if (input.equals("0")){
                    milk.setError("An empty field is not added to Cart");
                    return;
                }
                else {
                    milkQuantity=Integer.parseInt(input);
                    newNum = ++initial;
                    cartA.setText("" + newNum);
                    milk.getText().clear();
                    Toast.makeText(ProductShopping.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input=cartA.getText().toString();
                cartA.setText("");
                Intent i=new Intent(ProductShopping.this,ProductCart.class);
                i.putExtra(MILK_QUANTITY,milkQuantity);
                i.putExtra(SUKUMA_QUANTITY,sukumaQuantity);
                i.putExtra(EGGS_QUANTITY,eggsQuantity);
                i.putExtra(KUKU_QUANTITY,chickenQuantity);
                startActivity(i);
            }
        });

    }

    private void sendMail() {
        String subject="Sukumawiki";
        String text="I do not understand the "+subject+" packaging. Kindly help.";
        Intent i=new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc2822");
        i.putExtra(Intent.EXTRA_SUBJECT,subject);
        i.putExtra(Intent.EXTRA_TEXT,text);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==REQUEST_CODE && data!=null){
            Bundle extras= data.getExtras();
            Bitmap p=(Bitmap) extras.get("data");
            sukumaw.setImageBitmap(p);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId=item.getItemId();
        switch (itemId){
            case R.id.settings:{
                sendMail();
            }
            case R.id.log_out:{
                Toast.makeText(this,"Are you sure you want to log out?", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
