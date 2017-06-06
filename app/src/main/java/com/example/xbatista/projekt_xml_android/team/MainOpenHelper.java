package com.example.xbatista.projekt_xml_android.team;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xbatista on 18/05/2017.
 */

public class MainOpenHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "main.db";
    private static final int DATABASE_VERSION = 3;


    public MainOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Team.TABLE + "(" +
                Team.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Team.NAME + " TEXT NOT NULL, " +
                Team.ROUNDS + " INTEGER, " +
                Team.SCORE + " INTEGER" + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Team.TABLE); // nenasadzovať s týmto na google .. je to len vyvojarska varianta
        onCreate(db);
    }
}
