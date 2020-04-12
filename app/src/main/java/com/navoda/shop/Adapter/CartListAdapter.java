package com.navoda.shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.navoda.shop.R;
import com.navoda.shop.model.ListProductItem;
import com.navoda.shop.model.MainProduct;

import java.util.List;



public class CartListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<ListProductItem> data;
    private int type;


    public CartListAdapter(Context context, List<ListProductItem> data, int a){
        this.context = context;
//        this.layoutInflater = layoutInflater;
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
            convertView = layoutInflater.inflate(R.layout.cart_item, null);
        }

        TextView txt = convertView.findViewById(R.id.txtItemNum);
        TextView name = convertView.findViewById(R.id.txtName);
        TextView quen = convertView.findViewById(R.id.txtQuentity);

        ListProductItem obj = data.get(position);
//
        txt.setText(Integer.toString(position+1));
        name.setText(obj.getName());
        quen.setText(Integer.toString(obj.getQuentity()));

        return convertView;
    }
}
