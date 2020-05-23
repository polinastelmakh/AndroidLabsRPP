package com.example.lab6;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {

    private ArrayList<Item> list;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        list = new ArrayList<>();
        dbHelper = new DBHelper(SplashScreen.this);
        if (savedInstanceState == null)
            new PrefetchData().execute();
    }

    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
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
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Intent i = new Intent(SplashScreen.this, StoreFrontActivity.class);
            i.putParcelableArrayListExtra("listOfItems", list);
            startActivity(i);
            finish();
        }
    }
}
