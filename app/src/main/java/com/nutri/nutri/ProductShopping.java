package com.nutri.nutri;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
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
    Button buyNow,description;
    TextView productName,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_shopping);
        productImage = findViewById(R.id.product_image);
        buyNow = findViewById(R.id.buy_now);
        description = findViewById(R.id.description);
        productName = findViewById(R.id.product_name);
        amount = findViewById(R.id.amount);
        viewCart = findViewById(R.id.viewCart);
        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAmount();
            }
        });
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });



                 //   milkQuantity=Integer.parseInt(input);
                //Intent i=new Intent(ProductShopping.this,ProductCart.class);
              //  i.putExtra(MILK_QUANTITY,milkQuantity);
        getIntentPosition();
        fillData();
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

    private void getIntentPosition(){
        mPosition = getIntent().getIntExtra(ProductListAdapter.CURRENT_POSITION_VALUE,DEFAULT_POSITION);
    }
    private void fillData() {
        if (mPosition != DEFAULT_POSITION) {
            Product product = NavigationActivity.mProductArrayList.get(mPosition);
            productName.setText(product.getName());
            // productPrice.setText(product.getPrice());
            productImage.setImageResource(product.getImage());
            // productDescription.setText(product.getDescription());
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
      amount.setText("" + newVal);
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
