package com.navoda.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class MyOrdersActivity extends AppCompatActivity {

    ListView orderList;
    ProgressDialog progressDialog;
    List<MyOrdersMain>  dataM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

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

//                    try {
//                        JSONObject jsonObject = response.getJSONObject(i);
//
//                        MyOrdersMain item = new MyOrdersMain();
//                        item.setOrderID(jsonObject.getInt("orderID"));
//                        item.setRefNo(jsonObject.getString("refNo").toString());
//                        item.setShopName(jsonObject.getString("shopName").toString());
//                        item.setStatus(jsonObject.getString("status").toString());
//                        item.setShopID(jsonObject.getInt("shopID"));
////                        item.setItemList(jsonObject.get);
//
//                        for (MyOrderListItem itemS : jsonObject.getJSONArray("")){
//
//                        }
//                        dataM.add(item);

//                    }catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }

                setData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
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
}
