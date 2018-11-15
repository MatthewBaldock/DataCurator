package com.example.nbmb.datacurator.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DisableDataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "curator.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_DISABLE = "disablewifi";
    public static final String DISABLE_ID_COLUMN = "disableID";
    public static final String COLUMN_DUR = "duration";
    public static final String COLUMN_START = "startTime";
    public static final String COLUMN_TOGGLE = "toggle";

    private static final String DISABLE_TABLE_CREATE =
            "CREATE TABLE " + TABLE_DISABLE + " (" +
                    DISABLE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    COLUMN_DUR + " TEXT, "+
    COLUMN_START + " DATETIME, "+
    COLUMN_TOGGLE + " TEXT "+ ")";
    private static final String DISABLE_TABLE_DROP = "DROP TABLE IF EXISTS "+ TABLE_DISABLE;

    public static final String TABLE_DATAUSAGE = "datausage";
    public static final String DATA_ID_COLUMN = "dataID";
    public static final String COLUMN_DATAUSAGE_BOOT = "dataUsageSinceBoot";
    public static final String COLUMN_DATAUSAGE_QUERY = "dataUsageSinceQuery";

    private static final String DATAUSAGE_TABLE_CREATE =
            "CREATE TABLE " + TABLE_DATAUSAGE + " (" +
                    DATA_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    COLUMN_DATAUSAGE_BOOT + " INTEGER, "+
                    COLUMN_DATAUSAGE_QUERY + " INTEGER "+ ")";
    private static final String DATAUSAGE_TABLE_DROP = "DROP TABLE IF EXISTS "+ TABLE_DATAUSAGE;

    public DisableDataHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DISABLE_TABLE_CREATE);
        String row = "INSERT INTO "+TABLE_DISABLE +"(disableID,duration,toggle)VALUES(555,'','OFF')";
        db.execSQL(row);
        db.execSQL(DATAUSAGE_TABLE_CREATE);
        row = "INSERT INTO "+TABLE_DATAUSAGE +"(dataID,dataUsageSinceBoot,dataUsageSinceQuery)VALUES(33,0,0)";
        db.execSQL(row);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DISABLE_TABLE_DROP);
        db.execSQL(DATAUSAGE_TABLE_DROP);
        onCreate(db);
    }
    public void toggleOn(String duration,String startTime){
        SQLiteDatabase db = this.getWritableDatabase();
        String  toggleQuery = "UPDATE "+TABLE_DISABLE+" SET "+COLUMN_DUR+"='"+duration+"', "
                +COLUMN_START+"='"+startTime+"',"
                + COLUMN_TOGGLE+ "='ON' WHERE "+
                "disableID=555";
        db.execSQL(toggleQuery);
    }
    public void quickOn(String startTime){
        SQLiteDatabase db = this.getWritableDatabase();
        String  toggleQuery = "UPDATE "+TABLE_DISABLE+" SET "
                +COLUMN_START+"="+startTime+","
                + COLUMN_TOGGLE+ "='ON' WHERE "+
                "disableID=555";
        db.execSQL(toggleQuery);
    }
    public void toggleOff(){
        SQLiteDatabase db = this.getWritableDatabase();
        String  toggleQuery = "UPDATE "+TABLE_DISABLE+" SET "+ COLUMN_TOGGLE+ "='OFF' WHERE "+
                "disableID=555";
        db.execSQL(toggleQuery);
    }

    public int getDuration()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String getQuery = "SELECT "+COLUMN_DUR+" FROM "+TABLE_DISABLE+" WHERE "+ DISABLE_ID_COLUMN +"='555'";
        Cursor cursor = db.rawQuery(getQuery,null);
        int duration = 0;
        while(cursor.moveToNext())
        {
            duration = cursor.getInt(0);
        }
        return duration;
    }
    public String getStatus()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String getQuery = "SELECT "+COLUMN_TOGGLE+" FROM "+TABLE_DISABLE+" WHERE "+ DISABLE_ID_COLUMN +"='555'";
        Cursor cursor = db.rawQuery(getQuery,null);
        String onoff = "OFF";
        while(cursor.moveToNext())
        {
            onoff = cursor.getString(0);
        }
        return onoff;
    }

    public void setDataUsageBoot(long dataUsageSinceBoot) {
        SQLiteDatabase db = this.getWritableDatabase();
        String  updateQuery = "UPDATE "+TABLE_DATAUSAGE+" SET "+ COLUMN_DATAUSAGE_BOOT + "=" + dataUsageSinceBoot +" WHERE "+
                "dataID=33";
        db.execSQL(updateQuery);
    }

    public void setDataUsageQuery(long dataUsageSinceQuery) {
        SQLiteDatabase db = this.getWritableDatabase();
        String  updateQuery = "UPDATE "+TABLE_DATAUSAGE+" SET "+ COLUMN_DATAUSAGE_QUERY + "=" + dataUsageSinceQuery +" WHERE "+
                "dataID=33";
        db.execSQL(updateQuery);
    }

    public long getDataUsageBoot()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String getQuery = "SELECT "+COLUMN_DATAUSAGE_BOOT+" FROM "+TABLE_DATAUSAGE+" WHERE "+ DATA_ID_COLUMN +"='33'";
        Cursor cursor = db.rawQuery(getQuery,null);
        long dataUsageSinceBoot = 0;
        while(cursor.moveToNext())
        {
            dataUsageSinceBoot = cursor.getLong(0);
        }
        return dataUsageSinceBoot;
    }

    public long getDataUsageQuery()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String getQuery = "SELECT "+COLUMN_DATAUSAGE_QUERY+" FROM "+TABLE_DATAUSAGE+" WHERE "+ DATA_ID_COLUMN +"='33'";
        Cursor cursor = db.rawQuery(getQuery,null);
        long dataUsageSinceQuery = 0;
        while(cursor.moveToNext())
        {
            dataUsageSinceQuery = cursor.getLong(0);
        }
        return dataUsageSinceQuery;
    }
}
