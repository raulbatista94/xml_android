package com.example.xbatista.projekt_xml_android.team;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.xbatista.projekt_xml_android.R;


/**
 * Created by xbatista on 18/05/2017.
 */

public class TeamAdapter extends CursorAdapter{

    public TeamAdapter(Context context) {
        super(context, openCursor(context), true);
    }

    private static Cursor openCursor(Context context) {
        MainOpenHelper mainOpenHelper = new MainOpenHelper(context);
        SQLiteDatabase db = mainOpenHelper.getReadableDatabase();
        return db.query(Team.TABLE,
                null,
                null,
                null,
                null,
                null,
                null
        );



    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflanter = ((Activity)context).getLayoutInflater();
        View view = inflanter.inflate(R.layout.team_row, null, true);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.name = (TextView) view.findViewById(R.id.name);
        viewHolder.score = (TextView) view.findViewById(R.id.score);

        view.setTag(viewHolder); // ukazatel ulozeny do znacky
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Team team = new Team(cursor);
        ViewHolder viewHolder = (ViewHolder) view.getTag(); // vytahujem z ukazatela

        viewHolder.name.setText(team.getName() + team.getId());
        viewHolder.score.setText(String.valueOf(team.getScore()));

    }

    private static class ViewHolder {

        public TextView name;
        public TextView score;

    }
}
