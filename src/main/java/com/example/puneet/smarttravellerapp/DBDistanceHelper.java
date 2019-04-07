package com.example.puneet.smarttravellerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class DBDistanceHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "myDistanceDB.db";
    private static final int DB_VERSION = 1;

    private static final String PLACES_TABLE = "places";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_PlACE = "place";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_AVG_EST_TIME = "avg_est_time";

    private static final String CREATE_TABLE_PLACES = "CREATE TABLE " + PLACES_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PlACE + " TEXT,"
            + COLUMN_LATITUDE + " TEXT,"
            + COLUMN_LONGITUDE + " TEXT,"
            + COLUMN_LOCATION + " TEXT,"
            + COLUMN_AVG_EST_TIME + " TEXT);";

    //  private static final String CREATE_TABLE_PLACES = "CREATE TABLE places(_id INTEGER PRIMARY KEY AUTOINCREMENT,place TEXT NOT NULL,latitude TEXT NOT NULL,longitude TEXT NOT NULL,avg_est_time TEXT NOT NULL);";
    private static final String DROP_TABLE_PLACES = "DROP TABLE IF EXISTS " + PLACES_TABLE;

    DBDistanceHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d("smart traveller", "Places Table created");
        db.execSQL(CREATE_TABLE_PLACES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_PLACES);
        onCreate(db);
    }

    void addPlaces(String place, String latitude, String longitude,String location, String avg_est_time)
    {
        SQLiteDatabase db = getWritableDatabase();

        Log.d("smart traveller", "before adding Places to DBDistanceHelper");

        ContentValues values = new ContentValues();
        values.put("place", place);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("location",location);
        values.put("avg_est_time", avg_est_time);

        long id = db.insert("places", null, values);

        Distance bean = new Distance(place, latitude, longitude, location, avg_est_time);

        Log.d("smart traveller", "in addPlaces() of DBDistanceHelper - one row inserted " + id);
        Log.d("smart traveller", "in addPlaces() of DBDistanceHelper - one row inserted " + bean.toString());
        db.close();
    }

    List<Distance> getPlaces(String location, String avg_est_time)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        List<Distance> result = new ArrayList<>();

       // String[] tableColumns = new String[] {COLUMN_PlACE,COLUMN_LATITUDE,COLUMN_LONGITUDE};
        String[] whereArgs = new String[] {location, avg_est_time};
        //Cursor cursor = db.rawQuery(selectQuery, null);
        //Cursor cursor = db.query(PLACES_TABLE,null,"location1 = ? and avg_est_time = ?",new String[] {location1,avg_est_time},null,null,null);
        //cursor = db.query(true,PLACES_TABLE,tableColumns,"location1 = ? and avg_est_time = ?",new String[] {location1,avg_est_time},null,null,null,null);
        cursor = db.rawQuery("SELECT DISTINCT * FROM places " + " where location = ? AND avg_est_time = ? ",whereArgs);
        //String[] tableColumns = new String[] {COLUMN_PlACE,COLUMN_LATITUDE,COLUMN_LONGITUDE};

        //String whereClause = "location1 = ? AND avg_est_time = ?";
        //String[] whereArgs = new String[] {location1, avg_est_time};

        try {
            //String queryString = "SELECT  place , latitude , longitude FROM  places "+
              //      "WHERE location1 = ? AND avg_est_time = ? ";
            //cursor = db.rawQuery(queryString, whereArgs);
            //cursor = db.query(true, PLACES_TABLE, tableColumns, whereClause, whereArgs, null, null, null, null);

            // Move to first row
            cursor.moveToFirst();
            if (cursor.getCount() > 0)
            {
                while (!(cursor.isAfterLast()))
                {
                    int id = cursor.getInt(0);
                    String dbplace = cursor.getString(1);
                    String dblati = cursor.getString(2);
                    String dblongi = cursor.getString(3);
                    String dbloca = cursor.getString(4);
                    String dbavgtime = cursor.getString(5);

                    Log.d("smart traveller","One row retrieved "+id);
                    Distance d = new Distance(dbplace,dblati,dblongi,dbloca,dbavgtime);
                    result.add(d);

                    ///String latlong = cursor.getString(2) + "," + cursor.getString(3);
                    //Log.d("smart traveller", "in getPlaces() of DBDistanceHelper - fetching row data " + "lat & long " + latlong + " " + cursor.getCount());
                    cursor.moveToNext();
                }
            }
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.d("smart traveller", "No values Returned from DB, something bad happened "+e.getMessage());
        }
        finally
        {
            if (cursor!= null)
            cursor.close();
            db.close();
        }
        Log.d("smart traveller", "No values Returned from DB");
        return  null;
    }
}
