package com.example.xbatista.projekt_xml_android;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.xbatista.projekt_xml_android.team.MainRepository;
import com.example.xbatista.projekt_xml_android.team.Team;
import com.example.xbatista.projekt_xml_android.team.TeamAdapter;

public class MainActivity extends ListActivity {

    private MainRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        repository = new MainRepository(this);


        int r = (int) (Math.random() * 100);
        Team team = new Team("Janko");

        repository.insert(team);
        Toast.makeText(this, "Teams" + repository.findAllTeams().size(), Toast.LENGTH_SHORT).show();

    //LIST VIEW
        setListAdapter(new TeamAdapter(this));
    }




    @Override
    public  void onResume(){
        super.onResume();
        setListAdapter(new TeamAdapter(this));

    }
}
