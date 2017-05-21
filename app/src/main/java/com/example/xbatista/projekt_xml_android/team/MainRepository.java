package com.example.xbatista.projekt_xml_android.team;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xbatista on 18/05/2017.
 */

public class MainRepository {
    private MainOpenHelper mainOpenHelper;

    public MainRepository(Context context) { mainOpenHelper = new MainOpenHelper(context); }

    public long insert(Team team){
        SQLiteDatabase db = mainOpenHelper.getWritableDatabase();

        try{
            return db.insert(Team.TABLE, null, team.values);
        } finally {
            db.close();
        }

    }

    public List<Team> findAllTeams(){
        SQLiteDatabase db = mainOpenHelper.getReadableDatabase();

        try {
            List<Team> result = new LinkedList<>();
            Cursor c = db.query(Team.TABLE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);

            try {
                while ( c.moveToNext()) {
                    result.add(new Team(c));
                }
                return result;
            } finally {
                db.close();
            }
        }finally {
            db.close();
        }
    }

    public Team getById(long id) {
        SQLiteDatabase db = mainOpenHelper.getReadableDatabase();
        try{
            Cursor c = db.query(Team.TABLE,
                    null,
                    Team.ID + " = " + id,
                    null,
                    null,
                    null,
                    null

            );
            try {
                if (c.moveToNext())
                    return new Team(c);

                throw new IllegalArgumentException("Id is not a valid number");
            } finally {
                db.close();
            }
        } finally {
            db.close();
        }
    }
    public int update(Team team){
        SQLiteDatabase db = mainOpenHelper.getWritableDatabase();
        try {
            return db.update(Team.TABLE, team.values, Team.ID + " = " + team.getId(), null); //vraci mi to id insertu
        } finally {
            db.close();
        }

    }

}
