package com.navoda.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.navoda.shop.Adapter.MainAdapter;
import com.navoda.shop.model.MainProduct;
import com.navoda.shop.model.cart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductSearchActivity extends AppCompatActivity {

    GridView grid;
    List<MainProduct> Category;
    Button listViewBtn;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
        grid = findViewById(R.id.grid);
        listViewBtn = findViewById(R.id.viewlistbtn);
        Category = new ArrayList<>();
        getProductList();

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(),"nj n",Toast.LENGTH_SHORT).show();
                gotoNext(position);
            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        int itemcount = cart.cartArr.size();
        if (itemcount > 0){
            listViewBtn.setVisibility(View.VISIBLE);
        }else{
            listViewBtn.setVisibility(View.GONE);
        }
    }

    public void setData(){
        MainAdapter adapter = new MainAdapter(this, Category,0);
        grid.setAdapter(adapter);
    }

    public void getProductList(){
        String url = "http://lahiruat-29044.portmap.io:29044/grocery-core/api/customer/product-categories";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        MainProduct category = new MainProduct();
                        category.setCategoryId(jsonObject.getInt("categoryID"));
                        category.setCategoryName(jsonObject.getString("categoryName").toString());
                        category.setImg(jsonObject.getString("imageURL").toString());
                        Category.add(category);

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    public void gotoNext(int data) {
        int id = Category.get(data).getCategoryId();


        Intent i = new Intent(this,SecondSearchActivity.class);
        i.putExtra("CATEGORY_ID" , id);
        startActivity(i);
    }


    public void onTapViewList(View view) {
        Intent i = new Intent(this,ItemListActivity.class);
        startActivity(i);
    }
}
