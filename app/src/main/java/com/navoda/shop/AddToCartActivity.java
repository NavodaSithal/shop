package com.navoda.shop;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.navoda.shop.model.ListProductItem;
import com.navoda.shop.model.Product;
import com.navoda.shop.model.cart;

public class AddToCartActivity extends AppCompatActivity {
    TextView PName , Price , itemCount;
    int noOfItem = 1;
    ImageView img;
    Product item;
    ListProductItem cartItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        itemCount = findViewById(R.id.item_count);
        PName = findViewById(R.id.productName);
        img = findViewById(R.id.productImage);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        item = new Product();
        item.setName(extras.getString("NAME"));
        item.setId(extras.getInt("ID"));
        item.setImage(extras.getString("IMAGE"));
        item.setmType(extras.getString("MTYPE"));
        itemCount.setText(Integer.toString(noOfItem));
        PName.setText(item.getName());

        Resources res = getResources();
        String mDrawableName = "cof_1";
        int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
        Drawable drawable = res.getDrawable(resID );
        img.setImageDrawable(drawable);
    }

    public void onTapMinus(View view) {
        if (noOfItem != 1) {
            noOfItem--;
        }

        itemCount.setText(Integer.toString(noOfItem));
    }

    public void onTapPlus(View view) {
//        if (noOfItem != 1) {
//            noOfItem--;
//        }
        noOfItem++;
        itemCount.setText(Integer.toString(noOfItem));
    }

    public void onTapAddToList(View view) {
        cartItem = new ListProductItem();
        cartItem.setId(item.getId());
        cartItem.setName(item.getName());
        cartItem.setQuentity(noOfItem);

        cart.cartArr.add(cartItem);

        finish();
    }
}
