package com.navoda.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.navoda.shop.Adapter.MyOrderProductItemAdapter;
import com.navoda.shop.Adapter.ShopListAdapter;
import com.navoda.shop.model.MyOrdersMain;
import com.navoda.shop.model.ShopPrizeItem;

import androidx.appcompat.app.AppCompatActivity;

import static com.navoda.shop.R.drawable.text_status_pending;

public class MyOrderItemListActivity extends AppCompatActivity {

    TextView shopName , orderId , refNo , status;
    ListView list;
    MyOrdersMain obj;
    @SuppressLint("ResourceAsColor")
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

        String order_status = "Pending";

        if (obj.getStatus().equals("MERCHANT_CONFIRMED")){
            order_status = "Preparing";
            status.setTextColor(getResources().getColor(R.color.colorGreen));

        }else if (obj.getStatus().equals("MERCHANT_CANCELED") || obj.getStatus().equals("CANCELED")){
            order_status = "Canceled";
            status.setTextColor(getResources().getColor(R.color.colorPrimary));

        }else if (obj.getStatus().equals("CUSTOMER_PICKED_UP")){
            order_status = "Picked Up";
            status.setTextColor(getResources().getColor(R.color.colorPerple));

        }else{
            order_status = "Pending";
            status.setTextColor(getResources().getColor(R.color.colorAccent));
        }

        shopName.setText(obj.getShopName());
        orderId.setText(Integer.valueOf(obj.getOrderID()).toString());
        refNo.setText(obj.getRefNo());
        status.setText(order_status);

        MyOrderProductItemAdapter adapter = new MyOrderProductItemAdapter(this, obj.getItemList(),1);
        list.setAdapter(adapter);

    }
}
