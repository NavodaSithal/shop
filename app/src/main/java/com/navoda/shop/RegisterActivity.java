package com.navoda.shop;

import android.app.ProgressDialog;
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
import com.navoda.shop.model.tokenObj;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText user , email, mobile, passsword, confirmpsw, firstname;
    String psw,conp,username,email_,mobile_,first;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = findViewById(R.id.edtuser);
        email = findViewById(R.id.edtemail);
        mobile = findViewById(R.id.editmobile);
        passsword = findViewById(R.id.edtpsw);
        confirmpsw = findViewById(R.id.editconfirm);
        firstname = findViewById(R.id.edtfirst);



    }

    public void onTapCustomer(View view) {

        psw = passsword.getText().toString();
        conp = confirmpsw.getText().toString();

        username = user.getText().toString();
        email_ = email.getText().toString();
        mobile_ = mobile.getText().toString();
        first = firstname.getText().toString();

        if (username.isEmpty()){
            user.setError("Enter Username");
        }else if (email_.isEmpty()){
            email.setError("Enter Username");
        }else if (mobile_.isEmpty()){
            mobile.setError("Enter Username");
        }else if (psw.isEmpty()){
            passsword.setError("Enter Username");
        }else if (!psw.equals(conp)){
            confirmpsw.setError("Confirm Password mismatch");
        }else{
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Registering....");
            progressDialog.show();

            Register();
        }

    }

    public void Register(){
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("username", username);
            jsonBody.put("password", psw);
            jsonBody.put("email", email_);
            jsonBody.put("mobileNo", mobile_);
            jsonBody.put("firstName", first);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://lahiruat-29044.portmap.io:29044/grocery-core/api/customer/register";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                goToNext();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "112121212" , Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void goToNext(){
        Intent i = new Intent(this,LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void onTapShopper(View view) {
    }
}
