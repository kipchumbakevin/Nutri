package com.nutri.nutri;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.nutri.nutri.R.color.colorRed;
import static java.lang.Integer.valueOf;

public class ProductCart extends AppCompatActivity {
    private static final String CURRENT_POSITION = "com.nutri.nutri,first_Name";
    public static ArrayList<Product> mProductArrayList=new ArrayList<>();
    private Product[] productNames={};
    private String[] productAmounts={};
    CheckBox maziwa,mayai,mboga,kukuK,all;
    Button payment,pay;
    ImageView delete,cancelPop;
    TextView noCart,nameTotal,totalAmount,nameMilk,quantityMilk,totalMilk,milk,quantity,total,sukumaName,sukumaTotal,eggsName,eggsQuantity,eggsTotal,kukuName,kukuQuantity,kukuTotal;
    int milkItems,kukuItems,eggsItems,sukumaItems,kuku,sukuma,eggs,milkk;
    int totaly,totalMinus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        payment = findViewById(R.id.payment);
        nameTotal = findViewById(R.id.totalName);
        totalAmount = findViewById(R.id.total);
        milk = findViewById(R.id.milkName);
        quantity = findViewById(R.id.milkQuantity);
        total = findViewById(R.id.milkTotal);
        nameMilk = findViewById(R.id.nameMilk);
        quantityMilk = findViewById(R.id.quantityMilk);
        totalMilk = findViewById(R.id.totalMilk);
        sukumaName = findViewById(R.id.sukumaName);
        sukumaTotal = findViewById(R.id.sukumaTotal);
        eggsName = findViewById(R.id.eggsName);
        eggsQuantity  =findViewById(R.id.eggsQuantity);
        eggsTotal = findViewById(R.id.eggsTotal);
        kukuName = findViewById(R.id.kukuName);
        kukuQuantity = findViewById(R.id.kukuQuantity);
        delete = findViewById(R.id.deleteItem);
        kukuTotal = findViewById(R.id.kukuTotal);
        noCart = findViewById(R.id.noCart);
        maziwa = findViewById(R.id.checkBoxMilk);
        mboga = findViewById(R.id.checkBoxSukuma);
        mayai =  findViewById(R.id.checkBoxEggs);
        kukuK = findViewById(R.id.checkBoxChicken);
        all = findViewById(R.id.checkBoxAll);


        getCartItems();
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (all.isChecked()){
                    maziwa.setChecked(true);
                    mboga.setChecked(true);
                    mayai.setChecked(true);
                    kukuK.setChecked(true);
                }
                else {
                    maziwa.setChecked(false);
                    mboga.setChecked(false);
                    mayai.setChecked(false);
                    kukuK.setChecked(false);
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                all.setVisibility(View.INVISIBLE);
                String maz=Integer.toString(0);
                 kuku = 600 * kukuItems;
                 sukuma = sukumaItems;
                 eggs = 10 * eggsItems;
                 milkk = 60 * milkItems;
                 totaly=kuku+sukuma+eggs+milkk;


                if (maziwa.isChecked()){
                    totalMinus=kuku+sukuma+eggs-milkk;
                    milk.setText("");
                    quantity.setText(maz);
                    total.setText(maz);


                    maziwa.setVisibility(View.INVISIBLE);
                    total.setVisibility(View.INVISIBLE);
                    quantity.setVisibility(View.INVISIBLE);
                    milk.setVisibility(View.INVISIBLE);
                    maziwa.setChecked(false);
                }
                 if (mayai.isChecked()){
                    totalMinus=kuku+sukuma+milkk-eggs;
                     eggsName.setText("");
                     eggsQuantity.setText(maz);
                     eggsTotal.setText(maz);

                     mayai.setVisibility(View.INVISIBLE);
                    eggsTotal.setVisibility(View.INVISIBLE);
                    eggsQuantity.setVisibility(View.INVISIBLE);
                    eggsName.setVisibility(View.INVISIBLE);
                    mayai.setChecked(false);

                }
                 if (mboga.isChecked()){
                    sukumaItems=0;
                    sukumaName.setText("");
                    sukumaTotal.setText(maz);
                    totalMinus=+sukuma;
                     totalAmount.setText(String.valueOf(totaly-totalMinus));

                     mboga.setVisibility(View.INVISIBLE);
                    sukumaTotal.setVisibility(View.INVISIBLE);
                    sukumaName.setVisibility(View.INVISIBLE);
                    mboga.setChecked(false);

                }
                 if (kukuK.isChecked()){
                    kukuItems=0;
                    kukuName.setText("");
                    kukuQuantity.setText(maz);
                    kukuTotal.setText(maz);
                    totalAmount.setText("Ksh."+Integer.toString(totaly));
                    kukuK.setVisibility(View.INVISIBLE);
                    kukuTotal.setVisibility(View.INVISIBLE);
                    kukuQuantity.setVisibility(View.INVISIBLE);
                    kukuName.setVisibility(View.INVISIBLE);

                }

            }
        });
        milk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (milkItems>0||sukumaItems>0||eggsItems>0||kukuItems>0){
                    all.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.VISIBLE);}
                if (milkItems>0){
                    maziwa.setVisibility(View.VISIBLE);
               }
                if (sukumaItems>0){
                    mboga.setVisibility(View.VISIBLE);
                }
                if (eggsItems>0){
                    mayai.setVisibility(View.VISIBLE);
                }
                if (kukuItems>0){
                    kukuK.setVisibility(View.VISIBLE);
                }
            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            String input=totalAmount.getText().toString();
            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProductCart.this);
                View mView = getLayoutInflater().inflate(R.layout.popup, null);
                final EditText mEmail= mView.findViewById(R.id.email);
                final EditText phone=mView.findViewById(R.id.phoneNumber);
                final EditText firstName= mView.findViewById(R.id.firstName);
                final EditText lastName= mView.findViewById(R.id.lastName);
                final EditText otherName= mView.findViewById(R.id.otherName);
                final Button payUp= mView.findViewById(R.id.pay);
                final TextView pay= mView.findViewById(R.id.amount);
                final TextView head= mView.findViewById(R.id.head);
                pay.setText(input);
                payUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       fillDialog();
    }



    private void fillDialog(){
        if (mEmail.getText().toString().isEmpty()){
            mEmail.setError("This is a required field");
        }
        if (phone.getText().toString().isEmpty()){
            phone.setError("This is a required field");
        }if (firstName.getText().toString().isEmpty()){
            firstName.setError("This is a required field");
        }
        if(lastName.getText().toString().isEmpty()){
            lastName.setError("This is a required field");
        }else{
            String inputN = firstName.getText().toString();
            String inputM = lastName.getText().toString();
            Toast.makeText(ProductCart.this,"Thank you "+ inputN + " for shopping with us.",Toast.LENGTH_LONG).show();
            String num = phone.getText().toString();
            mEmail.getText().clear();
            phone.getText().clear();
            firstName.getText().clear();
            lastName.getText().clear();
            otherName.getText().clear();
            finish();




        }

    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog= mBuilder.create();
                dialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (all.isChecked()){
            all.setChecked(false);
            maziwa.setChecked(false);
            mboga.setChecked(false);
            mayai.setChecked(false);
            kukuK.setChecked(false);
        }
        else {
            super.onBackPressed();
        }
    }

    private void getCartItems() {
         milkItems = getIntent().getIntExtra(ProductShopping.MILK_QUANTITY, 0);
         sukumaItems = getIntent().getIntExtra(ProductShopping.SUKUMA_QUANTITY, 0);
         eggsItems = getIntent().getIntExtra(ProductShopping.EGGS_QUANTITY, 0);
         kukuItems = getIntent().getIntExtra(ProductShopping.KUKU_QUANTITY, 0);
            if (milkItems > 0) {
                milk.setVisibility(View.VISIBLE);
                milk.setText("MILK");
                quantity.setVisibility(View.VISIBLE);
                quantity.setText(Integer.toString(milkItems) + " ltrs");
                total.setVisibility(View.VISIBLE);
                total.setText( Integer.toString(milkItems * 60));

                // Toast.makeText(ProductCart.this,"milk ("+milkItems+")",Toast.LENGTH_LONG).show();
            }
            if (sukumaItems > 0) {
                sukumaName.setVisibility(View.VISIBLE);
                sukumaName.setText("SUKUMAWIKI");
                sukumaTotal.setVisibility(View.VISIBLE);
                sukumaTotal.setText("Ksh." + Integer.toString(sukumaItems));
            }
            if (eggsItems > 0) {
                Log.d("", "getCartItems: eggs");
                eggsName.setVisibility(View.VISIBLE);
                eggsName.setText("EGGS");
                eggsQuantity.setVisibility(View.VISIBLE);
                eggsQuantity.setText(Integer.toString(eggsItems));
                eggsTotal.setVisibility(View.VISIBLE);
                eggsTotal.setText("Ksh." + Integer.toString(eggsItems * 10));
            }
            if (kukuItems > 0) {
                kukuName.setVisibility(View.VISIBLE);
                kukuName.setText("CHICKEN");
                kukuQuantity.setVisibility(View.VISIBLE);
                kukuQuantity.setText(Integer.toString(kukuItems));
                kukuTotal.setVisibility(View.VISIBLE);
                kukuTotal.setText("Ksh." + Integer.toString(kukuItems * 600));
            }
            int kuku = 600 * kukuItems;
            int sukuma = sukumaItems;
            int eggs = 10 * eggsItems;
            int milk = 60 * milkItems;
            if (kukuItems > 0 || eggsItems > 0 || sukumaItems > 0 || milkItems > 0) {
                nameTotal.setVisibility(View.VISIBLE);
                nameTotal.setText("TOTAL");
                totalAmount.setVisibility(View.VISIBLE);
                totalAmount.setText("Ksh." + (kuku + sukuma + eggs + milk));
            }
            else{
                noCart.setVisibility(View.VISIBLE);
                noCart.setText("NO ITEM WAS ADDED TO CART");
            }

        }
}
