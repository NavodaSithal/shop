package com.navoda.shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.navoda.shop.model.MainPrizeListItem;
import com.navoda.shop.model.ShopPrizeItem;

public class ConfirmShopOrder extends AppCompatActivity {

    TextView shopName , Price , Distance ;
    ListView unAvailbleList;

    ShopPrizeItem obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_shop_order);

        shopName = findViewById(R.id.txt_shop_name_confirm);
        Price = findViewById(R.id.txt_total);
        Distance = findViewById(R.id.txt_distance);
        unAvailbleList = findViewById(R.id.list_noitem);

        Intent i = getIntent();
        String shop = i.getStringExtra("SHOP_LIST");
        Gson gson = new Gson();
        obj = gson.fromJson(shop, ShopPrizeItem.class);

        shopName.setText(obj.getShopName());
        Price.setText("Rs "+obj.getTotalValue() );
        Distance.setText(obj.getDistance());

        if (obj.getNotAvailableProductsList().size() == 0){
            unAvailbleList.setVisibility(View.GONE);
        }else{

        }
    }
}
