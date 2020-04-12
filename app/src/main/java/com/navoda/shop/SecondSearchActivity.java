package com.navoda.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.navoda.shop.Adapter.MainAdapter;
import com.navoda.shop.Adapter.ProductAdapter;
import com.navoda.shop.model.Product;
import com.navoda.shop.model.cart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SecondSearchActivity extends AppCompatActivity {

    GridView grid;
    List<Product> Products;
    String p_id = "0";
    Button btnView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_search);

        Bundle extras = getIntent().getExtras();
        int id= extras.getInt("CATEGORY_ID");
        p_id = Integer.toString(id);
        btnView = findViewById(R.id.view_list);

        grid = findViewById(R.id.grid_2);
        Products = new ArrayList<Product>();
        getData();
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(),"njknk n",Toast.LENGTH_SHORT).show();
                gotoAddToCart(position);
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
            btnView.setVisibility(View.VISIBLE);
        }else{
            btnView.setVisibility(View.GONE);
        }
    }

    public void setData(){
        ProductAdapter adapter = new ProductAdapter(this, Products,1);
        grid.setAdapter(adapter);
    }

    public void getData() {
        String url = "http://lahiruat-29044.portmap.io:29044/grocery-core/api/customer/product-sub-categories/" + p_id;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Product item = new Product();
                        item.setId(jsonObject.getInt("id"));
                        item.setCategoryCode(jsonObject.getString("subCategoryCode").toString());
                        item.setImage(jsonObject.getString("imageURL").toString());
                        item.setmType(jsonObject.getString("measureType").toString());
                        item.setName(jsonObject.getString("name").toString());

                        Products.add(item);

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

    public void gotoAddToCart(int position){

        Product item  = Products.get(position);
        Bundle extras = new Bundle();
        extras.putString("NAME",item.getName());
        extras.putInt("ID",item.getId());
        extras.putString("IMAGE",item.getImage());
        extras.putString("MTYPE",item.getmType());


        Intent i = new Intent(this,AddToCartActivity.class);
        i.putExtras(extras);
        startActivity(i);
    }

    public void onTapViewList(View view) {
        Intent i = new Intent(this,ItemListActivity.class);
        startActivity(i);
    }
}
