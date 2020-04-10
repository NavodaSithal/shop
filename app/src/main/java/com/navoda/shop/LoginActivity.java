package com.navoda.shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText username , psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        psw = findViewById(R.id.psw);

    }

    public void onTapLoginBtn(View view) {

        String user = "user";
        String pass = "user";

//        String user = username.getText().toString();
//        String pass = psw.getText().toString();

//        Call<ResponseBody> call = ApiClient.getInstance().getApi().signin(user,pass);
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                String s = null;
//                try {
//                    s = response.body().string();
////                    gotoNext();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                if (s != null){
//                    try {
//                        JSONObject jsonObject = new JSONObject(s);
//                        Toast.makeText(LoginActivity.this, jsonObject.getString("accessToken") , Toast.LENGTH_SHORT).show();
//
//                    }catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(LoginActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


//        Call<tokenObj> call = ApiClient.getInstance().getApi().signin(user,pass);
//
//        call.enqueue(new Callback<tokenObj>() {
//            @Override
//            public void onResponse(Call<tokenObj> call, Response<tokenObj> response) {
////                if (response.code() == 201){
//
//                    tokenObj t = response.body();
//                    Toast.makeText(LoginActivity.this, t.getType() , Toast.LENGTH_SHORT).show();
////                }else{
////                    Toast.makeText(LoginActivity.this, "dksav" , Toast.LENGTH_SHORT).show();
////
////                }
//            }
//
//            @Override
//            public void onFailure(Call<tokenObj> call, Throwable t) {
//
//            }
//        });

        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("username", user);
            jsonBody.put("password", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://lahiruat-29044.portmap.io:29044/grocery-core/api/auth/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(LoginActivity.this, "dksav" , Toast.LENGTH_SHORT).show();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "112121212" , Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void gotoNext(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}
