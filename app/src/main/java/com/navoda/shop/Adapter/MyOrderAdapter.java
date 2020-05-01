package com.navoda.shop.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
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

import androidx.core.content.ContextCompat;

import static com.navoda.shop.R.drawable.text_status_pending;


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

        String order_status = "Pending";

        if (obj.getStatus().equals("MERCHANT_CONFIRMED")){
            order_status = "Preparing";
            txt.setBackgroundResource(R.drawable.text_status_confirm);
        }else if (obj.getStatus().equals("MERCHANT_CANCELED") || obj.getStatus().equals("CANCELED")){
            order_status = "Canceled";
            txt.setBackgroundResource(R.drawable.text_status);
        }else if (obj.getStatus().equals("CUSTOMER_PICKED_UP")){
            order_status = "Picked Up";
            txt.setBackgroundResource(R.drawable.text_status_pickedup);
        }else{
            order_status = "Pending";
            txt.setBackgroundResource(text_status_pending);
//            txt.setPadding(10,0,10,0);
        }
        txt.setPaddingRelative(40,5,40,5);

        txt.setText(order_status);
        price.setText(Integer.valueOf(obj.getOrderID()).toString());
        shop.setText(obj.getShopName());
        refNo.setText(obj.getRefNo());

        return convertView;
    }
}
