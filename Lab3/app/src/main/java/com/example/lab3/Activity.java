package com.example.lab3;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;


public class Activity extends AppCompatActivity {
    private DataBase MyDB;
    private ListView listView;
    private ArrayList<String> str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        listView = findViewById(R.id.listView);
        ViewAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.text, str);
        listView.setAdapter(adapter);
    }

    public void ViewAll() {
        MyDB = new DataBase(this);
        Cursor res = MyDB.getInformation();
        if(res.getCount() == 0) {
            return;
        }
        str = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()) {
           buffer.append(res.getString(0) + "\n");
           buffer.append(res.getString(1) + "\n");
           buffer.append(res.getString(2));
           if (MyDB.getVersion() == 2 ) {
               buffer.append("\n");
               buffer.append(res.getString(3)+ "\n");
               buffer.append(res.getString(4));
           }
           str.add(buffer.toString());
           buffer  = new StringBuffer();
        }
        res.close();
    }
}
