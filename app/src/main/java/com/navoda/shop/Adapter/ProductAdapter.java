package com.navoda.shop.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.navoda.shop.R;
import com.navoda.shop.model.MainProduct;
import com.navoda.shop.model.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Product> data;

    private int type;
    public ProductAdapter(Context context, List<Product> data, int a){
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
            convertView = layoutInflater.inflate(R.layout.row_item, null);
        }

        String s = "R.drawable.cof_1";

        TextView txt = convertView.findViewById(R.id.txt);
        TextView price = convertView.findViewById(R.id.price);
        ImageView img = convertView.findViewById(R.id.img_view);

//        img.setImageResource(R.drawable.cof_1);

        Resources res = context.getResources();
        String mDrawableName = "cof_1";
        int resID = res.getIdentifier(mDrawableName , "drawable", context.getPackageName());
        Drawable drawable = res.getDrawable(resID );
        img.setImageDrawable(drawable);

        Product obj = data.get(position);
//
        txt.setText(obj.getName());
        if (type == 1){
//            price.setText(data.indexOf(position));
        }
        return convertView;
    }
}
