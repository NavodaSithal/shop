package com.navoda.shop;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.navoda.shop.model.MainPrizeListItem;
import com.navoda.shop.model.ShopPrizeItem;

public class ShopMapActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap map ;
    MainPrizeListItem obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_map);

        Intent i = getIntent();
        String shop = i.getStringExtra("DATA");
        Gson gson = new Gson();
        obj = gson.fromJson(shop, MainPrizeListItem.class);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        for (ShopPrizeItem itrm : obj.getShopListWithPrices()){
            LatLng latLng = new LatLng(Double.valueOf(itrm.getLat()) , Double.valueOf(itrm.getLon()));
            map.addMarker(new MarkerOptions().position(latLng).title("Rs "+ itrm.getTotalValue()).snippet(itrm.getShopName()));
//            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11f));

        }

        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                return false;
            }
        });


    }
}
