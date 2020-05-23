package com.example.lab6;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class StoreFrontActivity extends AppCompatActivity implements AdapterRecyclerView.OnNoteListener {
    private RecyclerView recyclerView;
    private AdapterRecyclerView adapterRecyclerView;
    private ArrayList<Item> list;
    private final String TAG = "gg";
    private TextView textView;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_front);

        recyclerView = findViewById(R.id.recycle_view);
        textView = findViewById(R.id.text_view_products_not);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        Intent i = getIntent();
        list= new ArrayList<>();
        if (i.hasExtra("listOfItems")) {
            list = i.getParcelableArrayListExtra("listOfItems");
            if (list.size() != 0) {
                adapterRecyclerView = new AdapterRecyclerView(this, list, this);
                textView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(adapterRecyclerView);
            } else {
                recyclerView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }
        } else {
            dbHelper = new DBHelper(StoreFrontActivity.this);
            database = dbHelper.getWritableDatabase();
            Cursor c = database.query(DBHelper.TABLE_PRODUCTS, null, null, null, null, null, null);
            if (c.moveToFirst()) {
                int idColIndex = c.getColumnIndex(DBHelper.KEY_ID);
                int nameColIndex = c.getColumnIndex(DBHelper.KEY_NAME);
                int priceColIndex = c.getColumnIndex(DBHelper.KEY_PRICE);
                int quantityColIndex = c.getColumnIndex(DBHelper.KEY_QUANTITY);
                do {
                    list.add(new Item(c.getInt(idColIndex), c.getString(nameColIndex), c.getFloat(priceColIndex),
                            c.getInt(quantityColIndex)));
                } while (c.moveToNext());
            }
            c.close();
            database.close();
            if (list.size() != 0) {
                adapterRecyclerView = new AdapterRecyclerView(this, list, this);
                textView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(adapterRecyclerView);
            } else {
                recyclerView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "clicked");
        Intent i = new Intent(StoreFrontActivity.this, ViewPagerActivity.class);
        i.putParcelableArrayListExtra("listOfItems", list);
        i.putExtra("position", position);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_store:
                Intent i = new Intent(StoreFrontActivity.this, BackEndActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
