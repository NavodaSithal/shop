package com.navoda.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.navoda.shop.model.CustomerObj;
import com.navoda.shop.model.ListProductItem;
import com.navoda.shop.model.MainPrizeListItem;
import com.navoda.shop.model.ShopPrizeItem;
import com.navoda.shop.model.cart;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmShopOrder extends AppCompatActivity {

    TextView shopName , Price , Distance , unavilable;
    ListView unAvailbleList;
    ProgressDialog progressDialog;

    ShopPrizeItem obj;

    int oId;
    String refNO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_shop_order);

        shopName = findViewById(R.id.txt_shop_name_confirm);
        Price = findViewById(R.id.txt_total);
        Distance = findViewById(R.id.txt_distance);
        unAvailbleList = findViewById(R.id.list_noitem);
        unavilable = findViewById(R.id.txt_unavailable);

        Intent i = getIntent();
        String shop = i.getStringExtra("SHOP");
        Gson gson = new Gson();
        obj = gson.fromJson(shop, ShopPrizeItem.class);


        oId = i.getIntExtra("OID",0);
        refNO = i.getStringExtra("REFNO");

        shopName.setText(obj.getShopName());
        Price.setText("Rs "+obj.getTotalValue() );
        Distance.setText(obj.getDistance());

        if (obj.getNotAvailableProductsList().size() == 0){
            unAvailbleList.setVisibility(View.GONE);
            unavilable.setVisibility(View.GONE);
        }else{

        }
    }

    public void onTapConfirm(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging....");
        progressDialog.show();

        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("orderID", oId);
            jsonBody.put("refNo", refNO);
            jsonBody.put("shopID", obj.getShopID());
            jsonBody.put("status", "CONFIRM");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url = "http://"+cart.subUrl+".ngrok.io/grocery-core/api/customer/"+ getCustomerId() +"/confirm-order";
        RequestQueue requestQueuenew = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                ShowSuccess();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ConfirmShopOrder.this, "Connection Error" , Toast.LENGTH_SHORT).show();
            }
        });

        requestQueuenew.add(request);
    }

    public void ShowSuccess(){
        saveList();


        Intent i = new Intent(this,MyOrdersActivity.class);
        startActivity(i);
    }


    public void saveList(){
        Gson gson = new Gson();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String productList = preferences.getString("PRODUCT_LIST", "");
        List<ListProductItem> objList;
//        List<ListProductItem> newListItem = new ArrayList<>();

        if (!productList.isEmpty()){
            Type type = new TypeToken<ArrayList<ListProductItem>>() {}.getType();
            objList =  gson.fromJson(productList, type);

            objList.addAll(cart.cartArr);

           /* for (ListProductItem item : cart.cartArr){
                int value = item.getQuentity();
                ListProductItem prodouct = new ListProductItem();
                for (ListProductItem i : objList){
                    if (item.getId() == i.getId()){
                        prodouct = i;
                        value = value + i.getQuentity();
                    }
                }
                if (value == item.getQuentity()){
                    newListItem.add(item);
                }else{
                   prodouct.setQuentity(value);
                   newListItem.add(prodouct);
                }
            }*/
        }else{
            objList = cart.cartArr;
        }

        SharedPreferences.Editor editor = preferences.edit();

        String json = gson.toJson(objList);

        editor.putString("PRODUCT_LIST", json);
        editor.apply();

        cart.cartArr.clear();

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
