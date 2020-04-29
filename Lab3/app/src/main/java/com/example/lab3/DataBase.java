package com.example.lab3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;


public class DataBase extends SQLiteOpenHelper {
    public static final String Database = "student.db";
    public static final String TableName = "student";
    public static int dbVersion = 1;
    public static int dbVersion2 = 1;
    public static final String FIO = "FIO";
    public static final String time = "time";


    public DataBase(Context context) {
        super(context,Database,null,dbVersion);
    }

    public void setVersion(int version) {
        dbVersion2 = version;
    }

    public int getVersion() {
        return dbVersion2;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop TABLE IF exists " + TableName);
        db.execSQL("create table " + TableName + "(id integer primary key AUTOINCREMENT,FIO TEXT,TIME time)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ArrayList<String> Name = new ArrayList<String>();
        ArrayList<String> Surname = new ArrayList<String>();
        ArrayList<String> Patronymic = new ArrayList<String>();
        if(oldVersion == 1 && newVersion == 2) {
            Cursor res;
            res = getInformation();
            if(res.getCount() == 0) {
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while(res.moveToNext()) {
                buffer.append( res.getString(1) + "\n");
                String[] parts = buffer.toString().trim().split(" ");
                Surname.add(parts[0]);
                Name.add(parts[1]);
                Patronymic.add(parts[2]);
                buffer  = new StringBuffer();
            }
            res.close();
            // Новая таблица с полями
            db.execSQL("Alter Table student REname To tmp_table_name");
            db.execSQL("create table " + TableName + "(id integer primary key AUTOINCREMENT,FAM TEXT,NAME TEXT,OTCH TEXT,TIME time)");
            db.execSQL("insert INTo student (TIME) select TIME FROM tmp_table_name");
            db.execSQL("Drop table tmp_table_name");
            for(int i = 0; i < Surname.size(); i++) {
                db.execSQL("UPDATE student SET FAM = '" + Surname.get(i)+"' WHERE id = " + (i+1));
                db.execSQL("UPDATE student SET NAME = '" + Name.get(i)+"' WHERE id = " + (i+1));
                db.execSQL("UPDATE student SET OTCH = '" + Patronymic.get(i)+"' WHERE id = " + (i+1));
            }
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        db.execSQL("drop TABLE " + TableName);
        db.execSQL("create table " + TableName + "(id integer primary key AUTOINCREMENT,FIO TEXT,TIME time)");
    }

    public boolean insertData(String FiO, String Time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIO,FiO);
        contentValues.put(time,Time);
        long result = db.insert(TableName,null,contentValues);
        if(result == -1) {
            return false;
        }
        else return true;
    }

    public Cursor getInformation() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TableName,null);
        return res;
    }
}
