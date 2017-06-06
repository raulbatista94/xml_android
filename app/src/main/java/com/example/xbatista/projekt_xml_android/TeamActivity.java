package com.example.xbatista.projekt_xml_android;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.xbatista.projekt_xml_android.team.MainRepository;
import com.example.xbatista.projekt_xml_android.team.Team;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.List;

/**
 * Created by Raul on 20-May-17.
 */


public class TeamActivity extends Activity {


    private static final String TAG = "PHOTO" ;
    Button start_button, stop_button, delete_button, correct_button, incorrect_button, button3, button4, button5, hide, show, camera;
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
    private String myData, actual_activity;
    private String[] activity = {"Draw", "Describe", "Pantomime"};
    private File imageFile = null;
    private static final int CONTENT_REQUEST=1337;



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
                case R.id.correct :
                    correct();
                    break;
                case R.id.incorrect :
                    incorrect();
                    break;
                case R.id.delete :
                    delete();
                    break;
                case R.id.show :
                    show_word();
                    break;
                case R.id.hide :
                    hide_word();
                    break;
                case R.id.camera_button :
                    dispatchTakePictureIntent();
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
            actual_activity = (activity[new Random().nextInt(activity.length)]);
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
        hide = (Button) findViewById(R.id.hide);
        hide.setOnClickListener(btnClickListener);
        show = (Button) findViewById(R.id.show);
        show.setOnClickListener(btnClickListener);
        start_button.setVisibility(View.VISIBLE);
        stop_button.setVisibility(View.INVISIBLE);
        guessWord.setVisibility(View.INVISIBLE);
        timer_id.setVisibility(View.INVISIBLE);
        hide.setVisibility(View.INVISIBLE);
        show.setVisibility(View.INVISIBLE);
        camera = (Button) findViewById(R.id.camera_button);
        camera.setOnClickListener(btnClickListener);
        camera.setVisibility(View.INVISIBLE);
        correct_button.setVisibility(View.INVISIBLE);
        incorrect_button.setVisibility(View.INVISIBLE);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        builder.detectFileUriExposure();






        repository = new MainRepository(this);
        Bundle extras = getIntent().getExtras();
        if (extras.containsKey(EXTRA_TEAM_ID)) {
            long teamId = extras.getLong(EXTRA_TEAM_ID);
            team = repository.getById(teamId);

            idView.setText(Long.toString(teamId));
            teamName.setText(team.getName() + " your activitity is: " + actual_activity);
            guessWord.setText(myData);
            System.out.print(myData);



        }

    }



    private void delete(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Do you really want to delete this team? ");
        dialog.setCancelable(false);

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                repository.delete(team);
                finish();
                }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

            }
    });
        dialog.create().show();
    }







    private void button3action(){
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        button5.setVisibility(View.INVISIBLE);
        guessWord.setVisibility(View.VISIBLE);
        timer_id.setVisibility(View.VISIBLE);
        delete_button.setVisibility(View.INVISIBLE);
        correct_button.setVisibility(View.VISIBLE);
        incorrect_button.setVisibility(View.VISIBLE);
        hide.setVisibility(View.VISIBLE);
        if (actual_activity == "Draw") {
            camera.setVisibility(View.VISIBLE);
        }

        possible_points = 3;

    }

    private void button4action(){
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        button5.setVisibility(View.INVISIBLE);
        guessWord.setVisibility(View.VISIBLE);
        timer_id.setVisibility(View.VISIBLE);
        correct_button.setVisibility(View.VISIBLE);
        incorrect_button.setVisibility(View.VISIBLE);
        delete_button.setVisibility(View.INVISIBLE);
        hide.setVisibility(View.VISIBLE);
        if (actual_activity == "Draw") {
            camera.setVisibility(View.VISIBLE);
        }

        possible_points = 4;

    }

    private void button5action(){
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        button5.setVisibility(View.INVISIBLE);
        guessWord.setVisibility(View.VISIBLE);
        delete_button.setVisibility(View.INVISIBLE);
        timer_id.setVisibility(View.VISIBLE);
        correct_button.setVisibility(View.VISIBLE);
        incorrect_button.setVisibility(View.VISIBLE);
        hide.setVisibility(View.VISIBLE);
        if (actual_activity == "Draw") {
            camera.setVisibility(View.VISIBLE);
        }

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








    private void show_word() {
        hide.setVisibility(View.VISIBLE);
        show.setVisibility(View.INVISIBLE);
        guessWord.setVisibility(View.VISIBLE);
    }

    private void hide_word() {
        hide.setVisibility(View.INVISIBLE);
        show.setVisibility(View.VISIBLE);
        guessWord.setVisibility(View.INVISIBLE);
    }









    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageFile = getFile();
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));


        startActivityForResult(takePictureIntent, CONTENT_REQUEST);
    }


    private File getFile(){
// saving image to custom directory only for this app
        File folder = new File("sdcard/camera_app");

        if (!folder.exists()){
            folder.mkdir();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File image_file = new File(folder, imageFileName + ".jpg");
        return image_file;


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path = "sdcard/camera_app/cam_image.jpg";
        if (requestCode == CONTENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "The image was saved to :" + imageFile.getAbsolutePath(), Toast.LENGTH_LONG).show();

                /*Intent takePictureIntent = new Intent(Intent.ACTION_VIEW); // showing image after pressing OK -- not necessary here
                takePictureIntent.setDataAndType(Uri.fromFile(imageFile), "image/jpeg");
                startActivity(takePictureIntent);
                finish();*/
            }
        }
    }
}
