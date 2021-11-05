package com.example.vendingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

import java.util.Locale;

public class CustomerView_3 extends AppCompatActivity {
    private TextToSpeech tts;
    private String txt="Enjoy your dink in a clear bottle,which is now more Recyclable";
    public MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_3);
        mp = MediaPlayer.create(CustomerView_3.this,R.raw.bgmusic);
        mp.setLooping(true);
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        switch (am.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                mp.setVolume(0.2f,0.2f);
                break;}
        tts = new TextToSpeech(getApplicationContext(), status -> {
            if(status == TextToSpeech.SUCCESS ){
                //select Language
                tts.setLanguage(Locale.ENGLISH);
                tts.setSpeechRate(0.8f);
                tts.setPitch(1);
                tts.speak(txt,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        mp.start();
    }
}