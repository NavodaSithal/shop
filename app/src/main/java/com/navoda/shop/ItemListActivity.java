package com.navoda.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.navoda.shop.Adapter.CartListAdapter;
import com.navoda.shop.Adapter.ProductAdapter;
import com.navoda.shop.model.CustomerObj;
import com.navoda.shop.model.ListProductItem;
import com.navoda.shop.model.MainPrizeListItem;
import com.navoda.shop.model.Prizereq;
import com.navoda.shop.model.Product;
import com.navoda.shop.model.cart;
import com.navoda.shop.model.itemreq;
import com.navoda.shop.model.tokenObj;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ItemListActivity extends AppCompatActivity {
    ListView listView ;

    List<ListProductItem> ProductList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        ProductList = cart.cartArr;

        listView = findViewById(R.id.listView);

        CartListAdapter adapter = new CartListAdapter(this, ProductList,1);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }
        });
    }

    public void ontapcheckout(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging....");
        progressDialog.show();

        List<itemreq> productItem = new ArrayList<>();

        for (int i = 0 ; i < ProductList.size() ; i++){

            ListProductItem item = ProductList.get(i);
            itemreq a = new itemreq(item.getId(),item.getQuentity()) ;
            productItem.add(a);
        }

        Prizereq req = new Prizereq("6.927079","79.861244", productItem);

        JSONObject jsonBody = new JSONObject();
        JSONArray array = new JSONArray();

        for (itemreq item : req.getProductList()){
            JSONObject jsontitem = new JSONObject();

            try {
                jsontitem.put("subProductID", item.getProductID());
                jsontitem.put("quantity", item.getQuantity());
                array.put(jsontitem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            jsonBody.put("latitude", req.getLatitude());
            jsonBody.put("longitude", req.getLongitude());
            jsonBody.put("productList", array);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonBody.toString();

        String url = "http://lahiruat-29044.portmap.io:29044/grocery-core/api/customer/"+ getCustomerId() +"/get-products-prices";
        RequestQueue requestQueuenew = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Toast.makeText(ItemListActivity.this, "dksav" , Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();

                MainPrizeListItem data = gson.fromJson(String.valueOf(response), MainPrizeListItem.class);
                gotoShopList(data);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ItemListActivity.this, "112121212" , Toast.LENGTH_SHORT).show();
            }
        });

        requestQueuenew.add(request);

    }

    public void gotoShopList(MainPrizeListItem item){

        Gson gson = new Gson();
        String json = gson.toJson(item);

        Intent i = new Intent(this, ShowShopList.class);
        i.putExtra("SHOP_LIST",json);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
