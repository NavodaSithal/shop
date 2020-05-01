package com.navoda.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.navoda.shop.Adapter.MainAdapter;
import com.navoda.shop.Adapter.MyOrderAdapter;
import com.navoda.shop.model.CustomerObj;
import com.navoda.shop.model.MainPrizeListItem;
import com.navoda.shop.model.MainProduct;
import com.navoda.shop.model.MyOrderListItem;
import com.navoda.shop.model.MyOrdersMain;
import com.navoda.shop.model.Product;
import com.navoda.shop.model.cart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MyOrdersActivity extends AppCompatActivity {

    ListView orderList;
    ProgressDialog progressDialog;
    List<MyOrdersMain>  dataM;
    int type = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        Bundle b = getIntent().getExtras();
        if (b != null){
            type = b.getInt("FROM");
        }

        orderList = findViewById(R.id.order_list);
        dataM = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToList(position);
            }
        });

        getMyOrders();
    }

    public void setData(){
        MyOrderAdapter adapter = new MyOrderAdapter(this,dataM,0);
        orderList.setAdapter(adapter);
    }

    public void getMyOrders(){
        String url = "http://"+cart.subUrl+".ngrok.io/grocery-core/api/customer/"+getCustomerId()+"/get-orders";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();
                for (int i = 0; i < response.length(); i++) {
                    Gson gson = new Gson();
                    MyOrdersMain data = null;
                    try {
                        data = gson.fromJson(String.valueOf(response.get(i)), MyOrdersMain.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dataM.add(data);

                }

                setData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MyOrdersActivity.this, "Connection Error" , Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void goToList(int position){
        MyOrdersMain obj = dataM.get(position);
        Gson gson = new Gson();
        String json = gson.toJson(obj);

        Intent i = new Intent(this,MyOrderItemListActivity.class);
        i.putExtra("ORDER" , json);
        startActivity(i);
    }


    public int getCustomerId(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = preferences.getString("CUSTOMER", "");
        CustomerObj obj = gson.fromJson(json, CustomerObj.class);

        int id = obj.getId();
        return id;
    }

    @Override
    public void onBackPressed() {
        if (type == 1) {
            super.onBackPressed();
        }else{
            Intent i = new Intent(this, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);

        }
    }
}
