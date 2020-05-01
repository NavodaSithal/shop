package com.navoda.shop;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class ItemListActivity extends AppCompatActivity {
    ListView listView;

    List<ListProductItem> ProductList;
    ProgressDialog progressDialog;
    String longitude, latitude;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        ProductList = cart.cartArr;

        listView = findViewById(R.id.listView);

        CartListAdapter adapter = new CartListAdapter(this, ProductList, 1);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        checkAndGetLocation();

    }

    public void checkShops(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging....");
        progressDialog.show();

        List<itemreq> productItem = new ArrayList<>();

        for (int i = 0 ; i < ProductList.size() ; i++){

            ListProductItem item = ProductList.get(i);
            itemreq a = new itemreq(item.getId(),item.getQuentity()) ;
            productItem.add(a);
        }

        Prizereq req = new Prizereq(latitude,longitude, productItem);

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

        String url = "http://"+cart.subUrl+".ngrok.io/grocery-core/api/customer/"+ getCustomerId() +"/get-products-prices";
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
                Toast.makeText(ItemListActivity.this, "Connection Error" , Toast.LENGTH_SHORT).show();
            }
        });

        requestQueuenew.add(request);

    }

    public void ontapcheckout(View view) {
        checkAndGetLocation();

    }

    public void checkAndGetLocation(){
        if (ActivityCompat.checkSelfPermission(ItemListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getLocation();
        }else{
            ActivityCompat.requestPermissions(ItemListActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null){
//                    Geocoder geocoder = new Geocoder(ItemListActivity.this, Locale.getDefault());
                    latitude = String.valueOf(location.getLatitude());
                    longitude = String.valueOf(location.getLongitude());

                    checkShops();

                }else{
                    Toast.makeText(ItemListActivity.this, "location data missing" , Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void gotoShopList(MainPrizeListItem item){

        saveList();

        Gson gson = new Gson();
        String json = gson.toJson(item);

        Intent i = new Intent(this, ShowShopList.class);
        i.putExtra("SHOP_LIST",json);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }


    public void saveList(){
//        Gson gson = new Gson();
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String productList = preferences.getString("PRODUCT_LIST", "");
//        List<ListProductItem>  objList;
//        if (!productList.isEmpty()){
//            Type type = new TypeToken<ArrayList<ListProductItem>>() {}.getType();
//             objList =  gson.fromJson(productList, type);
//            objList.addAll(ProductList);
//
//        }else{
//            objList = ProductList;
//        }
//
//
//        SharedPreferences.Editor editor = preferences.edit();
//
//        String json = gson.toJson(objList);
//
//        editor.putString("PRODUCT_LIST", json);
//        editor.apply();
    }



    public int getCustomerId(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = preferences.getString("CUSTOMER", "");
        CustomerObj obj = gson.fromJson(json, CustomerObj.class);


        int id = obj.getId();
        return id;
    }


    public void onTapClear(View view) {
        cart.cartArr.clear();

        Intent i = new Intent(this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
