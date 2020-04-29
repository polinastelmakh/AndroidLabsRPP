package com.example.lab3;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    DataBase MyDB;
    Button button1,button2,button3,button4,button5;
    SQLiteDatabase db;
    Date currentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDB = new DataBase(this);
        currentTime = Calendar.getInstance().getTime();
        db = MyDB.getWritableDatabase();
        db.execSQL("drop TABLE student");
        db.execSQL("create table student (id integer primary key AUTOINCREMENT,FIO TEXT,TIME time)");
        InsertData();

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
    }

    @Override
    protected void onResume(){
        super.onResume();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Activity.class);
                startActivity(i);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyDB.getVersion() != 2) {
                    MyDB.insertData("Сальникова Дарья Сергеевна",Calendar.getInstance().getTime().toString());
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyDB.getVersion() != 2) {
                db.execSQL("UPDATE student SET FIO = 'Иванов Иван Иванович' WHERE id = " + (DatabaseUtils.queryNumEntries(db, "student")) + ";");
            }}
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDB.onUpgrade(db,1,2);
                MyDB.setVersion(2);
                Toast.makeText(MainActivity.this, "Version db 2.0", Toast.LENGTH_SHORT).show();
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.execSQL("drop TABLE student");
                MyDB.setVersion(1);
                db.execSQL("create table student (id integer primary key AUTOINCREMENT,FIO TEXT,TIME time)");
                InsertData();
            }
        });
}

public void InsertData() {
    db.execSQL("UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = 'student';");
    MyDB.insertData("Стельмах Полина Геннадьевна",Calendar.getInstance().getTime().toString());
    MyDB.insertData("Сидоров Вадим Алексеевич",Calendar.getInstance().getTime().toString());
    MyDB.insertData("Попов Денис Геннадьевич",Calendar.getInstance().getTime().toString());
    }
}
