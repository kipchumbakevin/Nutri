package com.nutri.nutri;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KIPCHU on 04/07/2019.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolders>{
    public static final String CURRENT_POSITION_VALUE = "com.nutri.nutri.current_value" ;
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final ArrayList<Product> mProductArrayList;

    public ProductListAdapter(Context context, ArrayList<Product> productArrayList){
        mContext= context;
        mProductArrayList=productArrayList;
        mLayoutInflater= LayoutInflater.from(mContext);
    }

    @NonNull

    @Override
    public ProductListAdapter.ProductViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=mLayoutInflater.inflate(R.layout.item_product,viewGroup, false);
        return new ProductViewHolders(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolders productViewHolders, int position) {
        Product product=mProductArrayList.get(position);
        productViewHolders.productName.setText(product.getName());
        productViewHolders.productImage.setImageResource(product.getImage());
        productViewHolders.mCurrentPosition=position;


    }

    @Override
    public int getItemCount() {
        return mProductArrayList.size();
    }

    public class ProductViewHolders extends RecyclerView.ViewHolder {
        private final ImageView productImage;
        private final TextView productName;
        Button addToCart;
        public  int mCurrentPosition;


        public ProductViewHolders(View itemView) {
            super(itemView);
            productName=(TextView) itemView.findViewById(R.id.product_name);
            productImage=(ImageView) itemView.findViewById(R.id.product_image);
            addToCart = itemView.findViewById(R.id.goToCart);
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, ProductShopping.class);
                    intent.putExtra(CURRENT_POSITION_VALUE, mCurrentPosition);
                    mContext.startActivity(intent);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, ProductShopping.class);
                    intent.putExtra(CURRENT_POSITION_VALUE, mCurrentPosition);
                    mContext.startActivity(intent);
                }
            });


        }
    }
}
