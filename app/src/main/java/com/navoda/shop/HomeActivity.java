package com.navoda.shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onTapProductSearch(View view) {
        Intent i = new Intent(this,ProductSearchActivity.class);
        startActivity(i);
    }


    public void onTapProductListBtn(View view) {
    }

    public void onTapOrders(View view) {

    }

    public void onTapAccountBtn(View view) {

    }
}
