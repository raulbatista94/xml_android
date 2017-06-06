package com.example.xbatista.projekt_xml_android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xbatista.projekt_xml_android.team.MainRepository;
import com.example.xbatista.projekt_xml_android.team.Team;

/**
 * Created by Raul on 20-May-17.
 */






public class CreateTeam extends Activity {

    private EditText name;
    private Team team;

    public static final String EXTRA_ID = "cz.mendelu.xbatista.projekt_xml.createTeam.teamID";
    private MainRepository repository;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);
        name = (EditText) findViewById(R.id.create_name);

        //save.setOnClickListener(btnClickListener);

        repository = new MainRepository(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflanter = getMenuInflater();
        inflanter.inflate(R.menu.team_menu, menu);
        return true;
    }

    public void saveTeam(MenuItem item){
        team = new Team(name.getText().toString());
        repository.insert(team);
        Toast.makeText(this, "Team has been created", Toast.LENGTH_LONG).show();
        finish();
    }


}
