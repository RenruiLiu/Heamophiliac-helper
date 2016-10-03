package com.assignment2;

//Renrui Liu 216166456, SIT207 assignment2.

/*
This is the DataBaseHelper to build a SQLite database to store injection records.
 *  */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DateBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "InjectionRecord.db";
    public static final String TABLE_NAME = "injection";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Time";
    public static final String COL_3 = "Type";

    public DateBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT NOT NULL UNIQUE," + COL_3 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String time, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, time);
        contentValues.put(COL_3, type);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public void delete(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " where ID='" + ID + "'");
    }

    //get data and sort them with latest date comes first.
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " ORDER BY Time desc", null);
        return res;
    }


}