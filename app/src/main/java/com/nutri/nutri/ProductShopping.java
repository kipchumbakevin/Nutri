package com.nutri.nutri;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductShopping extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    private static final int REQUEST_CODE = 433;
    private static final int DEFAULT_POSITION = -2;
    public static final String MILK_QUANTITY = "com.nutri.nutri.milk_quantity";
    public static final String SUKUMA_QUANTITY = "com.nutri.nutri.sukuma_quantity";
    public static final String EGGS_QUANTITY = "com.nutri.nutri.eggs_quantity";
    public static final String KUKU_QUANTITY = "com.nutri.nutri.kuku_quantity";
    private int mPosition;
    ImageView productImage,viewCart;
    Button buyNow,description,hideDescription;
    TextView productName,amount,productDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_shopping);
        productImage = findViewById(R.id.product_image);
        buyNow = findViewById(R.id.buy_now);
        description = findViewById(R.id.description);
        productName = findViewById(R.id.product_name);
        amount = findViewById(R.id.amount);
        productDescription = findViewById(R.id.productDescription);
        viewCart = findViewById(R.id.viewCart);
        hideDescription = findViewById(R.id.hideDescription);
        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAmount();
            }
        });
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog();
                popupDialog();
            }
        });

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setTextColor(getResources().getColor(R.color.colorGreen));
                productDescription.setVisibility(View.VISIBLE);
                hideDescription.setVisibility(View.VISIBLE);
            }
        });
        hideDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setTextColor(getResources().getColor(R.color.colorWhite));
                productDescription.setVisibility(View.GONE);
                hideDescription.setVisibility(View.GONE);
            }
        });
        getIntentPosition();
        fillData();
        setTitle("Shopping");
    }

    private void dialog() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProductShopping.this);
        View mView = getLayoutInflater().inflate(R.layout.checkout,null);
        NumberPicker np = mView.findViewById(R.id.numberPicker);
        ImageView done = mView.findViewById(R.id.done);
        ImageView cancel = mView.findViewById(R.id.cancel);
        TextView title = mView.findViewById(R.id.numberPickerTitle);
        np.setMaxValue(100);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(ProductShopping.this);
        title.setText(productName.getText().toString());
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                int num = Integer.parseInt(amount.getText().toString());
                if (num>0){
                    amount.setVisibility(View.VISIBLE);
                    Toast.makeText(ProductShopping.this, "Added to cart", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("");
                dialog.dismiss();
            }
        });
    }
    private void popupDialog(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProductShopping.this);
        View mView = getLayoutInflater().inflate(R.layout.popupdisplay,null);
        TextView dialogTitle = mView.findViewById(R.id.dialogTitle);
        ImageView doneShopping = mView.findViewById(R.id.doneShopping);
        ImageView cancelShopping = mView.findViewById(R.id.cancelShopping);
        final Spinner spinner = mView.findViewById(R.id.spinner);
        final RadioButton kienyeji = mView.findViewById(R.id.kienyeji);
        final RadioButton broiler = mView.findViewById(R.id.broiler);
        NumberPicker numberPicker = mView.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(100);
        numberPicker.setMinValue(0);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(ProductShopping.this);
        dialogTitle.setText(productName.getText().toString());
        List<String> spinnerArray =  new ArrayList<String>();
                spinnerArray.add("Full");
                spinnerArray.add("Half");
                spinnerArray.add("Quarter");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);

        mBuilder.setView(mView);
        final AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
        kienyeji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kienyeji.isChecked()){
                    kienyeji.setChecked(false);
                }if (broiler.isChecked()){
                    broiler.setChecked(false);
                    kienyeji.setChecked(true);
                }else {
                    kienyeji.setChecked(true);
                }
            }
        });
        broiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (broiler.isChecked()){
                    broiler.setChecked(false);
                }if (kienyeji.isChecked()){
                    kienyeji.setChecked(false);
                    broiler.setChecked(true);
                }else{
                    broiler.setChecked(true);
                }
            }
        });

        doneShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kienyeji.isChecked() || broiler.isChecked()) {
                    String type = "";
                    String quantity = spinner.getSelectedItem().toString();
                    if (kienyeji.isChecked()) {
                        type = kienyeji.getText().toString();
                    }
                    if (broiler.isChecked()) {
                        type = broiler.getText().toString();
                    }
                    int amt = Integer.parseInt(amount.getText().toString());
                    alertDialog.dismiss();
                    if(Integer.parseInt(amount.getText().toString())>0) {
                        Toast.makeText(ProductShopping.this, amt + " " + quantity + " piece(s) of " + type + " chicken added to cart", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(ProductShopping.this, "No item was added to cart",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    kienyeji.setError("");
                    broiler.setError("");
                }
            }
        });

        cancelShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                amount.setText("");
            }
        });
    }

    private void getIntentPosition(){
        mPosition = getIntent().getIntExtra(ProductListAdapter.CURRENT_POSITION_VALUE,DEFAULT_POSITION);
    }
    private void fillData() {
        if (mPosition != DEFAULT_POSITION) {
            Product product = NavigationActivity.mProductArrayList.get(mPosition);
            productName.setText(product.getName());
           // productImage.setImageResource(product.getImage());
            Glide.with(this)
                    .load(Uri.parse(product.getImage()))
                    .into(productImage);
            productDescription.setText(product.getDescribe());
        }
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

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if (newVal == 0){
            amount.setText("");
        }else {
            amount.setText("" + newVal);
        }

    }
    public void getAmount(){
        Intent i = new Intent(ProductShopping.this,ProductCart.class);
        int milkItems = Integer.parseInt(amount.getText().toString());
        int sukumaItems = Integer.parseInt(amount.getText().toString());
        int kukuItems = Integer.parseInt(amount.getText().toString());
        int eggsItems = Integer.parseInt(amount.getText().toString());
        i.putExtra(EGGS_QUANTITY,eggsItems);
        i.putExtra(MILK_QUANTITY,milkItems);
        i.putExtra(SUKUMA_QUANTITY,sukumaItems);
        i.putExtra(KUKU_QUANTITY,kukuItems);
        startActivity(i);
    }
}
