package com.example.xbatista.projekt_xml_android.team;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.provider.BaseColumns;

/**
 * Created by xbatista on 18/05/2017.
 */

public class Team {

    static final String TABLE = "team";
    static final String ID = BaseColumns._ID;
    static final String NAME = "name";
    static final String SCORE = "score";
    static final String ROUNDS = "rounds";

    protected ContentValues values;

    public Team(String name){
        this.values = new ContentValues();
        this.setName(name);
        this.setRound(0);
        this.setScore(0);
    }

    protected Team(Cursor cursor){
        values = new ContentValues();
        DatabaseUtils.cursorRowToContentValues(cursor, values);
    }



    public long getId() {return values.getAsLong(ID);}

    public void setName(String name) { values.put(NAME, name);}
    public String getName() { return values.getAsString(NAME); }

    public void setScore(int score) {values.put(SCORE, score);}
    public String getScore() { return values.getAsString(SCORE);}

    public void setRound(int round) {values.put(ROUNDS, round);}
    public String getRounds() { return values.getAsString(ROUNDS);}


}
