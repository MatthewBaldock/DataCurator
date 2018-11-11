package com.example.nbmb.datacurator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DisableDataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "curator.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_DISABLE = "disablewifi";
    public static final String COLUMN_ID = "disableID";
    public static final String COLUMN_DUR = "duration";
    public static final String COLUMN_START = "startTime";
    public static final String COLUMN_TOGGLE = "toggle";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_DISABLE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    COLUMN_DUR + " TEXT, "+
                    COLUMN_START + " DATETIME, "+
                    COLUMN_TOGGLE + " TEXT "+ ")";
    private static final String TABLE_DROP = "DROP TABLE IF EXISTS "+ TABLE_DISABLE;
    public DisableDataHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TABLE_CREATE);
        String row = "INSERT INTO "+TABLE_DISABLE +"(disableID,duration,toggle)VALUES(555,'','OFF')";
        db.execSQL(row);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(TABLE_DROP);
        onCreate(db);
    }
    public void toggleOn(String duration,String startTime){
        SQLiteDatabase db = this.getWritableDatabase();
        String  toggleQuery = "UPDATE "+TABLE_DISABLE+" SET "+COLUMN_DUR+"='"+duration+"', "
                +COLUMN_START+"="+startTime+","
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


}
