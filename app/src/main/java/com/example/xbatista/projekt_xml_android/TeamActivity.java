package com.example.xbatista.projekt_xml_android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.VoiceInteractor;
import android.icu.util.TimeUnit;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xbatista.projekt_xml_android.team.MainRepository;
import com.example.xbatista.projekt_xml_android.team.Team;
import com.example.xbatista.projekt_xml_android.team.TeamAdapter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static java.lang.System.in;

/**
 * Created by Raul on 20-May-17.
 */


public class TeamActivity extends Activity {



    Button start_button, stop_button, delete_button;
    TextView timer_id;
    TextView idView;
    TextView teamName;
    TextView guessWord;
    public static final String EXTRA_TEAM_ID = "cz.mendelu.xbatista.projekt_XML.TeamActivity.teamId";
    private MainRepository repository;
    private Team team;
    private long pauseValue = 0;
    private CountDownTimer countDownTimer;


    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            switch(v.getId()){
                case R.id.start_counter :
                    start();
                    break;
                case R.id.stop_counter :
                    stop();
                    break;
                case R.id.delete :
                    delete();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_activity);

        start_button = (Button) findViewById(R.id.start_counter);
        start_button.setOnClickListener(btnClickListener);
        stop_button = (Button) findViewById(R.id.stop_counter);
        stop_button.setOnClickListener(btnClickListener);
        delete_button = (Button) findViewById(R.id.delete);
        delete_button.setOnClickListener(btnClickListener);
        timer_id = (TextView) findViewById(R.id.timer_id);
        idView = (TextView) findViewById(R.id.idView);
        teamName = (TextView) findViewById(R.id.teamName);
        guessWord = (TextView) findViewById(R.id.guessWord);





        repository = new MainRepository(this);
        Bundle extras = getIntent().getExtras();
        if(extras.containsKey(EXTRA_TEAM_ID)) {
            long teamId = extras.getLong(EXTRA_TEAM_ID);
            team = repository.getById(teamId);

            idView.setText(Long.toString(teamId));
            teamName.setText(team.getName());




        }

        }



    private void delete() {
        repository.delete(team);
        finish();
    }




    private void start() {
        if (pauseValue == 0){
        countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer_id.setText(""+millisUntilFinished / 1000 + " Seconds");
                pauseValue = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                timer_id.setText("Time is over!");
            }
        };
        }else {
            countDownTimer = new CountDownTimer(pauseValue, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    timer_id.setText("" + millisUntilFinished / 1000 + " Seconds");
                    pauseValue = millisUntilFinished;

                }

                @Override
                public void onFinish() {
                    timer_id.setText("Time is over!");

                }
            };

        }
        countDownTimer.start();
    }

    private void stop() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

}
