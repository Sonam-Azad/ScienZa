package com.example.android.simplefeeder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_CREATE_TABLE="CREATE TABLE "+ DatabaseContract.DataBaseEntries.TABLE_NAME+"("
            + DatabaseContract.DataBaseEntries._ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
            DatabaseContract.DataBaseEntries.COLUMN_TITLE+" TEXT ,"+
            DatabaseContract.DataBaseEntries.COLUMN_DESCRIPTION+" TEXT ,"+
            DatabaseContract.DataBaseEntries.COLUMN_IMAGEURL+" TEXT ,"+
            DatabaseContract.DataBaseEntries.COLUMN_URL+" TEXT ,"+
            DatabaseContract.DataBaseEntries.COLUMN_DATE+" TEXT ,"+
            DatabaseContract.DataBaseEntries.COLUMN_STATE+" INTEGER "+")";
    private static final String DATABASE_NAME="Scienza.db";
    private static final int DATABASE_VERSION=20;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("db","creating table ");
       db.execSQL(DATABASE_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL( "DROP TABLE IF EXISTS " + DatabaseContract.DataBaseEntries.TABLE_NAME);
      onCreate(db);
    }
}
