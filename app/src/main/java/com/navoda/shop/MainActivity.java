package com.navoda.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.navoda.shop.model.cart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTapRegBtn(View view) {
        if (readFile().isEmpty()){
            Toast.makeText(MainActivity.this, "URL missing" , Toast.LENGTH_SHORT).show();
        }else{
            Intent i = new Intent(this,RegisterActivity.class);
            startActivity(i);
        }
    }

    public void onTapLoginBtn(View view) {
        if (readFile().isEmpty()){
            Toast.makeText(MainActivity.this, "URL missing" , Toast.LENGTH_SHORT).show();
        }else{
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
        }
    }


    public void setUrl(View view) {
        Intent i = new Intent(this,SetUrlCode.class);
        startActivity(i);
    }

    private String readFile() {
        File fileEvents = new File(MainActivity.this.getFilesDir()+"/text/sample");
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileEvents));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
//                text.append('\n');
            }

            br.close();
        } catch (IOException e) { }
        String result = text.toString();
        cart.subUrl = result;
        return  result;
    }
}
