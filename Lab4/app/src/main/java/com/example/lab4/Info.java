package com.example.lab4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Info {
    private static Info instance;
    DataBase helper;
    SQLiteDatabase database;
    SimpleDateFormat format;

    private Info(Context context) {
        helper = new DataBase(context);
        database = helper.getWritableDatabase();
        format = new SimpleDateFormat("dd/MM/yyyy hh");
    }

    public static Info createInstance(Context context) {
        if(instance == null) {
            instance = new Info(context);
        }
        return instance;
    }

    public void insertDate(Date date) {
        database.delete("INFO", null, null);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        date = calendar.getTime();
        ContentValues values = new ContentValues();
        values.put("DATE", format.format(date));
        database.insert("INFO", null, values);
    }

    public Date getDate() {
        Cursor cursor = database.query("INFO", null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            int dateColumn = cursor.getColumnIndex("DATE");
            String result = cursor.getString(dateColumn);
            try {
                return format.parse(result);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        return  null;
    }

    public static Info getInstance() {
        return instance;
    }
}
