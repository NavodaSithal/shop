package com.navoda.shop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.navoda.shop.Adapter.CartListAdapter;
import com.navoda.shop.Adapter.ShopListAdapter;
import com.navoda.shop.model.CustomerObj;
import com.navoda.shop.model.MainPrizeListItem;
import com.navoda.shop.model.ShopPrizeItem;
import com.navoda.shop.model.cart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ShowShopList extends AppCompatActivity {

    ListView list;
    MainPrizeListItem obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shop_list);

        list = findViewById(R.id.listShop);

        Intent i = getIntent();
        String shop = i.getStringExtra("SHOP_LIST");
        Gson gson = new Gson();
        obj = gson.fromJson(shop, MainPrizeListItem.class);

        ShopListAdapter adapter = new ShopListAdapter(this, obj.getShopListWithPrices(),1);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition     = position;

                // ListView Clicked item value
//                String  itemValue    = (String) list.getItemAtPosition(position);
//                Toast.makeText(getApplicationContext(),
//                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
//                        .show();

                showConfirmView(itemPosition);

            }
        });

    }

    public void showConfirmView(int position){

        ShopPrizeItem item = obj.getShopListWithPrices().get(position);

        Gson gson = new Gson();
        String json = gson.toJson(item);

        Intent i = new Intent(this, ConfirmShopOrder.class);
        i.putExtra("SHOP",json);
        i.putExtra("REFNO",obj.getRefNo());
        i.putExtra("OID",obj.getOrderID());
        startActivity(i);
    }

    public void ShowMap(View view) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);

        Intent i = new Intent(this,ShopMapActivity.class);
        i.putExtra("DATA",json);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        showAlert();
    }

    public void showAlert(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Do you want to clear this cart");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
                        gotoHome();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void gotoHome(){
        cart.cartArr.clear();

        Intent i = new Intent(this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }
}
