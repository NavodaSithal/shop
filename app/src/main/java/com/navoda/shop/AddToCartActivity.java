package com.navoda.shop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddToCartActivity extends AppCompatActivity {
    TextView PName , Price , itemCount;
    int noOfItem = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        itemCount = findViewById(R.id.item_count);
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
}
