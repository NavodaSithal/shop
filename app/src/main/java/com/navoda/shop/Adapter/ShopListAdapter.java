package com.navoda.shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.navoda.shop.R;
import com.navoda.shop.model.ListProductItem;
import com.navoda.shop.model.MainPrizeListItem;
import com.navoda.shop.model.ShopPrizeItem;

import java.util.List;


public class ShopListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<ShopPrizeItem> data;
    private int type;


    public ShopListAdapter(Context context, List<ShopPrizeItem> data, int a){
        this.context = context;
        this.data = data;
        this.type = a;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.shop_item, null);
        }

        TextView name = convertView.findViewById(R.id.txt_shop_name);
        TextView price = convertView.findViewById(R.id.tex_item_price);
        TextView dis = convertView.findViewById(R.id.txt_dis);

        ShopPrizeItem obj = data.get(position);
//
        name.setText(obj.getShopName());
        price.setText(obj.getTotalValue());
        dis.setText(obj.getDistance());

        return convertView;
    }
}