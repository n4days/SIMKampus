package com.example.simkampus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "User.db";
    public static final String TABLE_NAME = "user_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "EMAIL";
    public static final String COL_3 = "PASSWORD";

    private static final String TABLE_NAME_2 = "mahasiswa_table";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NIM = "NIM";
    private static final String COLUMN_NAME = "NAMA";
    private static final String COLUMN_DOB = "DOB";
    private static final String COLUMN_GENDER = "GENDER";
    private static final String COLUMN_ADDRESS = "ADDRESS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, PASSWORD TEXT)");
        db.execSQL("create table " + TABLE_NAME_2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NIM TEXT, NAMA TEXT, DOB TEXT, GENDER TEXT, ADDRESS TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(db);
    }

    public boolean insert(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, email);
        contentValues.put(COL_3, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ?", new String[]{email});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ? AND PASSWORD = ?", new String[]{email, password});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public long addData(InfoMahasiswa data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NIM, data.getNIM());
        values.put(COLUMN_NAME, data.getNAMA());
        values.put(COLUMN_DOB, data.getDOB());
        values.put(COLUMN_GENDER, data.getGENDER());
        values.put(COLUMN_ADDRESS, data.getADDRESS());
        long id = db.insert(TABLE_NAME_2, null, values);
        db.close();
        return id;
    }

}
