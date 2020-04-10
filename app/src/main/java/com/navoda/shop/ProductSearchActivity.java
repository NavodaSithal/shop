package com.navoda.shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class ProductSearchActivity extends AppCompatActivity {

    GridView grid;
    String[] data = {"1", "3" , "3", "44" , "3" , "3", "44", "43"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
        grid = findViewById(R.id.grid);

        MainAdapter adapter = new MainAdapter(this, data,0);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"nj n",Toast.LENGTH_SHORT).show();
                gotoNext("nvjf");
            }
        });

    }

    public void gotoNext(String data) {
        Intent i = new Intent(this,SecondSearchActivity.class);
        startActivity(i);
    }


    public void onTapViewList(View view) {
        Intent i = new Intent(this,ItemListActivity.class);
        startActivity(i);
    }
}
