package com.example.xbatista.projekt_xml_android;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xbatista.projekt_xml_android.team.MainRepository;
import com.example.xbatista.projekt_xml_android.team.Team;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.List;

/**
 * Created by Raul on 20-May-17.
 */


public class TeamActivity extends Activity {



    Button start_button, stop_button, delete_button, correct_button, incorrect_button, button3, button4, button5;
    TextView timer_id;
    TextView idView;
    TextView teamName;
    TextView guessWord;
    private int possible_points;
    public static final String EXTRA_TEAM_ID = "cz.mendelu.xbatista.projekt_XML.TeamActivity.teamId";
    private MainRepository repository;
    private Team team;
    private long pauseValue = 0;
    private CountDownTimer countDownTimer;
    private String myData;


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
                case R.id.correct :
                    correct();
                    break;
                case R.id.incorrect :
                    incorrect();
                    break;
            }
        }
    };

    private View.OnClickListener pointsClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button3 :
                    button3action();
                    break;
                case R.id.button4 :
                    button4action();
                    break;
                case R.id.button5 :
                    button5action();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            Scanner sc = new Scanner(getAssets().open("random.txt"));
            List<String> lines = new ArrayList<String>();
            while (sc.hasNextLine()){
                lines.add(sc.nextLine());
            }
            String[] myWords = lines.toArray(new  String[0]);
            myData = (myWords[new Random().nextInt(myWords.length)]);
        } catch (IOException e) {
            e.printStackTrace();
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_activity);

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(pointsClickListener);
        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(pointsClickListener);
        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(pointsClickListener);
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
        correct_button = (Button) findViewById(R.id.correct);
        correct_button.setOnClickListener(btnClickListener);
        incorrect_button = (Button) findViewById(R.id.incorrect);
        incorrect_button.setOnClickListener(btnClickListener);
        start_button.setVisibility(View.VISIBLE);
        stop_button.setVisibility(View.INVISIBLE);
        guessWord.setVisibility(View.INVISIBLE);
        timer_id.setVisibility(View.INVISIBLE);





        repository = new MainRepository(this);
        Bundle extras = getIntent().getExtras();
        if (extras.containsKey(EXTRA_TEAM_ID)) {
            long teamId = extras.getLong(EXTRA_TEAM_ID);
            team = repository.getById(teamId);

            idView.setText(Long.toString(teamId));
            teamName.setText(team.getName());
            guessWord.setText(myData);
            System.out.print(myData);



        }

    }



    private void delete() {
        repository.delete(team);
        finish();
    }



    private void button3action(){
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        button5.setVisibility(View.INVISIBLE);
        guessWord.setVisibility(View.VISIBLE);
        timer_id.setVisibility(View.VISIBLE);
        possible_points = 3;

    }

    private void button4action(){
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        button5.setVisibility(View.INVISIBLE);
        guessWord.setVisibility(View.VISIBLE);
        timer_id.setVisibility(View.VISIBLE);
        possible_points = 4;

    }

    private void button5action(){
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        button5.setVisibility(View.INVISIBLE);
        guessWord.setVisibility(View.VISIBLE);
        timer_id.setVisibility(View.VISIBLE);
        possible_points = 5;

    }




    private void correct(){
        if (Integer.valueOf(team.getScore()) < 60) {
            team.setRound(+1);
            team.setScore(Integer.valueOf(team.getScore()) + possible_points);
            repository.update(team);
            Toast.makeText(this, "Correct you won " + possible_points + " points", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, team.getName() + " you already won with " + team.getScore()+ " points!", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    private void incorrect(){
        Toast.makeText(this, "Better luck next time :(", Toast.LENGTH_LONG).show();
        finish();

    }




    private void start() {
        start_button.setVisibility(View.INVISIBLE);
        stop_button.setVisibility(View.VISIBLE);
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
        start_button.setVisibility(View.VISIBLE);
        stop_button.setVisibility(View.INVISIBLE);
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

}
