package com.navoda.shop;

import android.app.ProgressDialog;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.navoda.shop.Adapter.CartListAdapter;
import com.navoda.shop.Adapter.ProductAdapter;
import com.navoda.shop.model.ListProductItem;
import com.navoda.shop.model.Product;
import com.navoda.shop.model.cart;
import com.navoda.shop.model.tokenObj;

import org.json.JSONException;
import org.json.JSONObject;

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

        String url = "http://lahiruat-29044.portmap.io:29044/grocery-core/api/auth/signin";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Toast.makeText(ItemListActivity.this, "dksav" , Toast.LENGTH_SHORT).show();
                tokenObj tobj = new tokenObj();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ItemListActivity.this, "112121212" , Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}
