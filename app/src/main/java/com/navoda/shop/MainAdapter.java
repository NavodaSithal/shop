package com.navoda.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private String[] data;
    private int type;
    public MainAdapter(Context context, String[] data, int a){
        this.context = context;
//        this.layoutInflater = layoutInflater;
        this.data = data;
        this.type = a;
    }

    @Override
    public int getCount() {
        return data.length;
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
            convertView = layoutInflater.inflate(R.layout.row_item, null);
        }

        TextView txt = convertView.findViewById(R.id.txt);
        TextView price = convertView.findViewById(R.id.price);
        ImageView img = convertView.findViewById(R.id.img_view);

        txt.setText(data[position]);
        if (type == 1){
            price.setText(data[position]); 
        }
        return convertView;
    }
}
