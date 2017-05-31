package com.example.xbatista.projekt_xml_android;

import android.animation.ObjectAnimator;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xbatista.projekt_xml_android.team.MainRepository;
import com.example.xbatista.projekt_xml_android.team.Team;
import com.example.xbatista.projekt_xml_android.team.TeamAdapter;

import static android.R.attr.id;

public class MainActivity extends ListActivity {



    private MainRepository repository;
    private Team team;
    Button delete_team;
    LinearLayout layout;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.team_row);


    //LIST VIEW
        setListAdapter(new TeamAdapter(this));
        delete_team = (Button) findViewById(R.id.delete_team);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflanter = getMenuInflater();
        inflanter.inflate(R.menu.add_menu, menu);
        return true;
    }

        public void addTeam(MenuItem item){
            Intent i = new Intent(this, CreateTeam.class);
            startActivity(i);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, final long id) {
        System.out.println("test");
        Intent i = new Intent(this, TeamActivity.class);
        i.putExtra(TeamActivity.EXTRA_TEAM_ID, id);
        startActivity(i);
    }

    @Override
    public  void onResume(){
        super.onResume();
        setListAdapter(new TeamAdapter(this));
    }
}
