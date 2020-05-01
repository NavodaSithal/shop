package com.navoda.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
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
import com.navoda.shop.model.Prizereq;
import com.navoda.shop.model.ShopPrizeItem;
import com.navoda.shop.model.cart;
import com.navoda.shop.model.itemreq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    List<ListProductItem> checkList;
    TextView txtMore , txtPrice, txtP1, txtP2 , txtP3;
    View predict_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtMore = findViewById(R.id.txt_more);
        txtPrice = findViewById(R.id.txt_price_home);
        txtP1 = findViewById(R.id.txt_1);
        txtP2 = findViewById(R.id.txt_2);
        txtP3 = findViewById(R.id.txt_3);
        predict_view = findViewById(R.id.view_predict);
        predict_view.setVisibility(View.GONE);

        checkPastOrders();
    }

    public void onTapProductSearch(View view) {
        Intent i = new Intent(this,ProductSearchActivity.class);
        startActivity(i);
    }


    public void onTapProductListBtn(View view) {
        Intent i = new Intent(this,TextRec.class);
        startActivity(i);
    }

    public void onTapOrders(View view) {
        Intent i = new Intent(this,MyOrdersActivity.class);
        i.putExtra("FROM" ,1);
        startActivity(i);

    }

    public void onTapAccountBtn(View view) {
        Intent i = new Intent(this,Profile.class);
        startActivity(i);
    }

    public void checkPastOrders(){
        Gson gson = new Gson();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String productList = preferences.getString("PRODUCT_LIST", "");
        List<ListProductItem> objList;
        checkList = new ArrayList<>();
        List<Integer> idList = new ArrayList<>();
        if (!productList.isEmpty()){
            Type type = new TypeToken<ArrayList<ListProductItem>>() {}.getType();
            objList =  gson.fromJson(productList, type);

            List<itemreq> reqestList = new ArrayList<>();

            for(int i= 0 ; i < objList.size() ; i++){
                int count = 1;
                int quentity  = objList.get(i).getQuentity();
                ListProductItem item = new ListProductItem();
                for(int j = 0 ; j < objList.size() ; j++){
                    if (i != j){
                        if (objList.get(i).getId() == objList.get(j).getId()){
                            count++;
                            quentity = quentity + objList.get(j).getQuentity();
                        }
                    }
                }

                if (idList.isEmpty()){
                    int value = 0;
                    if (quentity%count == 1){
                        value = (quentity/count);
                    }else if (quentity%count > 1){
                        value = (quentity/count) + 1;
                    }

                    if (count > 2){
                        idList.add(objList.get(i).getId());
                        item = objList.get(i);
                        item.setQuentity(value);
                        checkList.add(item);
                    }
                }else if (!idList.contains(objList.get(i).getId())){
                    int value = 0;
                    if (quentity%count == 0){
                        value = (quentity/count);
                    }else if (quentity%count > 1){
                        int a = (quentity/count);
                        value = (quentity/count) + 1;
                    }

                    if (count > 2 && value > 0){
                        idList.add(objList.get(i).getId());
                        item = objList.get(i);
                        item.setQuentity(value);
                        checkList.add(item);
                    }
                }
            }

            for (int i = 0 ; i < checkList.size() ; i++){
                ListProductItem item = checkList.get(i);
                itemreq a = new itemreq(item.getId(),item.getQuentity()) ;
                reqestList.add(a);
            }

            if (reqestList.size() > 0){
                checkShops(reqestList);
            }else{
                predict_view.setVisibility(View.GONE);
            }
        }
    }

    public void checkShops(List<itemreq> productItem){
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Logging....");
//        progressDialog.show();

//        List<itemreq> productItem = new ArrayList<>();

//        for (int i = 0 ; i < ProductList.size() ; i++){
//
//            ListProductItem item = ProductList.get(i);
//            itemreq a = new itemreq(item.getId(),item.getQuentity()) ;
//            productItem.add(a);
//        }

        Prizereq req = new Prizereq("7.242713","80.698486", productItem);

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
//                progressDialog.dismiss();
//                Toast.makeText(ItemListActivity.this, "dksav" , Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
                MainPrizeListItem data = gson.fromJson(String.valueOf(response), MainPrizeListItem.class);
                showData(data);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                Toast.makeText(HomeActivity.this, "112121212" , Toast.LENGTH_SHORT).show();
            }
        });

        requestQueuenew.add(request);

    }


    public void showData(MainPrizeListItem data){
        predict_view.setVisibility(View.VISIBLE);

        List<ShopPrizeItem> shopListWithPrices =  data.getShopListWithPrices();
        ShopPrizeItem item  = shopListWithPrices.get(0);

        if (checkList.size() > 3){
            txtMore.setVisibility(View.VISIBLE);
        }else{
            txtMore.setVisibility(View.GONE);
        }

        txtP1.setVisibility(View.GONE);
        txtP2.setVisibility(View.GONE);
        txtP3.setVisibility(View.GONE);

        for (int i = 0 ; i < checkList.size() ; i++){
            if (i == 0){
                txtP1.setText(checkList.get(i).getName());
                txtP1.setVisibility(View.VISIBLE);
            }else if (i == 1){
                txtP2.setText(checkList.get(i).getName());
                txtP2.setVisibility(View.VISIBLE);
            }else if (i == 2){
                txtP3.setText(checkList.get(i).getName());
                txtP3.setVisibility(View.VISIBLE);
            }
        }


        txtPrice.setText("Rs "+item.getTotalValue());
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
        super.onBackPressed();
    }
}
