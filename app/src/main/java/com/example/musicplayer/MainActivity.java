package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer myMusic;
    AudioManager myManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myMusic = MediaPlayer.create(this,R.raw.testaudio);



        //Get context from Audio Service
        myManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int myMaxVol = myManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int myCurrentVol = myManager.getStreamVolume(AudioManager.STREAM_MUSIC);


        //Set context to seekbar - VolumeRocker

        final SeekBar volumeRocker = findViewById(R.id.seekBar);
        volumeRocker.setMax(myMaxVol);
        volumeRocker.setProgress(myCurrentVol);


        //set a listener on volumeRocker

        volumeRocker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                myManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Timeline part of music part

        final SeekBar timeline = findViewById(R.id.timeline);
        timeline.setMax(myMusic.getDuration());

        timeline.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                myMusic.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //customize timeline seekbar here

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeline.setProgress(myMusic.getCurrentPosition());
            }
        }, 0, 1000);
    }

    public  void playMe(View view){
        myMusic.start();


    }

    public void pauseMe(View view){
        myMusic.pause();
    }

    public void stopMe(View view){
        myMusic.stop();

        myMusic = MediaPlayer.create(this,R.raw.testaudio);
    }
}