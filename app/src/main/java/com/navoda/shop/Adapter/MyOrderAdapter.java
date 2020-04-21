package com.navoda.shop.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.navoda.shop.R;
import com.navoda.shop.model.MainProduct;
import com.navoda.shop.model.MyOrdersMain;

import java.util.List;


public class MyOrderAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<MyOrdersMain> data;

    private int type;
    public MyOrderAdapter(Context context, List<MyOrdersMain> data, int a){
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
            convertView = layoutInflater.inflate(R.layout.my_order_item, null);
        }

        MyOrdersMain obj  = data.get(position);

        TextView txt = convertView.findViewById(R.id.txt_status);
        TextView price = convertView.findViewById(R.id.txt_price);
        TextView shop = convertView.findViewById(R.id.txt_shop);
        TextView refNo = convertView.findViewById(R.id.txt_ref_no);

        Drawable background = txt.getBackground();


        if (obj.getStatus() == ""){
            if (background instanceof ShapeDrawable) {
                ((ShapeDrawable)background).getPaint().setColor(ContextCompat.getColor(context,R.color.white));
            }
        }

        txt.setText(obj.getStatus());
        price.setText(Integer.valueOf(obj.getOrderID()).toString());
        shop.setText(obj.getShopName());
        refNo.setText(obj.getRefNo());

        return convertView;
    }
}
