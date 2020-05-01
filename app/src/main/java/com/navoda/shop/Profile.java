package com.navoda.shop;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.navoda.shop.model.CustomerObj;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    TextView username , firstname , lastname, mobile, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = findViewById(R.id.txt_username);
        firstname = findViewById(R.id.txt_first);
        lastname = findViewById(R.id.txt_last);
        mobile = findViewById(R.id.txt_mobile);
        email = findViewById(R.id.txt_email);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = preferences.getString("CUSTOMER", "");
        CustomerObj obj = gson.fromJson(json, CustomerObj.class);

        String user = preferences.getString("USERNAME", "");
        username.setText(user);

        firstname.setText(obj.getFirstname());
        if (obj.getLastName().isEmpty() || obj.getLastName().equals("null")){
            lastname.setText("");
        }else{
            lastname.setText(obj.getLastName());
        }
        mobile.setText(obj.getMobileNo());
        email.setText(obj.getEmail());

    }
}
