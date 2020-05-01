package com.navoda.shop;

import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.navoda.shop.model.CustomerObj;
import com.navoda.shop.model.cart;
import com.navoda.shop.model.tokenObj;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username , psw;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        psw = findViewById(R.id.psw);
    }

    public void onTapLoginBtn(View view) {

        String user = username.getText().toString();
        String pass = psw.getText().toString();

//        gotoNext();


//        String user = "saman";
//        String pass = "123456";

        if (user.isEmpty()){
            username.setError("Enter username");
        }else if (pass.isEmpty()){
            psw.setError("Enter Password");
        }else{
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Logging....");
            progressDialog.show();

            JSONObject jsonBody = new JSONObject();

            try {
                jsonBody.put("username", user);
                jsonBody.put("password", pass);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = "http://"+cart.subUrl+".ngrok.io/grocery-core/api/customer/login";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
//                    Toast.makeText(LoginActivity.this, "dksav" , Toast.LENGTH_SHORT).show();
                    CustomerObj cus = new CustomerObj();
                    try {
                        cus.setFirstname(response.getString("firstName").toString());
                        cus.setLastName(response.getString("lastName").toString());
                        cus.setMobileNo(response.getString("mobileNo").toString());
                        cus.setStatus(response.getString("status").toString());
                        cus.setEmail(response.getString("email").toString());
                        cus.setId(response.getInt("id"));
                        saveCustomer(cus);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Connection Error" , Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(jsonObjectRequest);
        }

    }

    public void saveUsername(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("USERNAME", username.getText().toString());
        editor.apply();


    }


    public void saveCustomer(CustomerObj obj){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(obj);

        editor.putString("CUSTOMER", json);
        editor.apply();
        saveUsername();
        gotoNext();


    }


    public  void getToken(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("accToken", "");

//        if(!name.equalsIgnoreCase(""))
//        {
//            name = name + "  Sethi";  /* Edit the value here*/
//        }
    }


    public void gotoNext(){
        Intent i = new Intent(this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }
}
