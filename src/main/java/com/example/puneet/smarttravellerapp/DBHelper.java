package com.example.puneet.smarttravellerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "myApp.db";
    private static final int DB_VERSION = 1;

    private static final String USER_TABLE = "users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASS = "password";
    private static final String COLUMN_CONFIRM_PASS = "confirm_password";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASS + " TEXT,"
            + COLUMN_CONFIRM_PASS + " TEXT);";

     DBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    void addUser(String name,String email, String password, String confirmPass)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASS, password);
        values.put(COLUMN_CONFIRM_PASS, confirmPass);

        long id = db.insert(USER_TABLE, null, values);
        db.close();

        Log.d("smart traveller", "in addUser() of DBHelper - one row inserted " +id);
    }

    boolean getUser(String email, String pass)
    {
        //HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "select * from  " + USER_TABLE + " where " + COLUMN_EMAIL + " = " + "'"+email+"'" + " and " + COLUMN_PASS + " = " + "'"+pass+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
        {
            String name = cursor.getString(1);
            User.setName(name);

            Log.d("smart traveller","in getUser() of DBHelper - fetching row data "+"name "+name+" "+cursor.getCount());
            return true;
        }
        cursor.close();
        db.close();

        return false;
    }


}
