package com.navoda.shop;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
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


public class ItemListActivity extends AppCompatActivity implements LocationListener {
    ListView listView;

    List<ListProductItem> ProductList;
    ProgressDialog progressDialog;
    String longitude, latitude;

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

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new ItemListActivity();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
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

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getBaseContext(), "Location changed: Lat: " + location.getLatitude() + " Lng: "
                        + location.getLongitude(), Toast.LENGTH_SHORT).show();
        longitude = String.valueOf(location.getLongitude()) ;
        latitude = String.valueOf(location.getLatitude()) ;

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
