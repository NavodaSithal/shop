package com.navoda.shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.navoda.shop.Adapter.MyOrderProductItemAdapter;
import com.navoda.shop.Adapter.ShopListAdapter;
import com.navoda.shop.model.MyOrdersMain;
import com.navoda.shop.model.ShopPrizeItem;

public class MyOrderItemListActivity extends AppCompatActivity {

    TextView shopName , orderId , refNo , status;
    ListView list;
    MyOrdersMain obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_item_list);

        shopName = findViewById(R.id.txt_order_shop);
        orderId = findViewById(R.id.txt_order_id);
        refNo = findViewById(R.id.txt_order_ref);
        status = findViewById(R.id.txt_status_order);
        list = findViewById(R.id.order_products_list);

        Intent i = getIntent();
        String shop = i.getStringExtra("ORDER");
        Gson gson = new Gson();
        obj = gson.fromJson(shop, MyOrdersMain.class);

        shopName.setText(obj.getShopName());
        orderId.setText(Integer.valueOf(obj.getOrderID()).toString());
        refNo.setText(obj.getRefNo());
        status.setText(obj.getStatus());

        MyOrderProductItemAdapter adapter = new MyOrderProductItemAdapter(this, obj.getItemList(),1);
        list.setAdapter(adapter);

    }
}
