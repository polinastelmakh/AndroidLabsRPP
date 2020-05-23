package com.example.lab7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class AdapterPager extends PagerAdapter {

    private ArrayList<Item> list;
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public AdapterPager(Context context, ArrayList<Item> list) {
        this.context = context;
        this.list = list;
        this.dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.page, container, false);

        TextView name = view.findViewById(R.id.vp_name);
        TextView price = view.findViewById(R.id.vp_price);
        final TextView quantity = view.findViewById(R.id.vp_quantity);
        final Button btn_buy = view.findViewById(R.id.vp_btn_buy);

        name.setText(list.get(position).getName());
        price.setText(Float.toString(list.get(position).getPrice()));
        quantity.setText(Integer.toString(list.get(position).getQuantity()));
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = dbHelper.getWritableDatabase();
                database.execSQL("UPDATE " + DBHelper.TABLE_PRODUCTS + " SET " +
                        DBHelper.KEY_QUANTITY + " = " + DBHelper.KEY_QUANTITY + " - 1"
                        + " WHERE " + DBHelper.KEY_ID + " = " + list.get(position).getID() + ";");
                database.close();
                Toast toast = Toast.makeText(context, "Товар \"" + list.get(position).getName()
                        + "\" куплен", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,
                        0,
                        200);
                toast.show();
                list.get(position).setQuantity(list.get(position).getQuantity() - 1);
                quantity.setText(Integer.toString(list.get(position).getQuantity()));
                if (list.get(position).getQuantity() <= 0) {
                    btn_buy.setEnabled(false);
                    database = dbHelper.getWritableDatabase();
                    database.execSQL("DELETE FROM " + DBHelper.TABLE_PRODUCTS + " WHERE " + DBHelper.KEY_ID
                            + " = " + list.get(position).getID() + ";");
                    database.close();
                    list.remove(position);
                }
                notifyDataSetChanged();
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }


}
