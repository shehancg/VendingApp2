package com.example.vendingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

import java.util.Locale;

public class tryagain extends AppCompatActivity {
    TextView tv;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tryagain);
        tv = (TextView) findViewById(R.id.textView14);
        String text=tv.getText().toString();

        tts = new TextToSpeech(getApplicationContext(), status -> {
            if(status == TextToSpeech.SUCCESS ){
                //select Language

                tts.setLanguage(Locale.ENGLISH);
                tts.setSpeechRate(0.8f);
                tts.setPitch(1);
                tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        final Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                rdtm();
            }
        }, 12000);
    }
    private void rdtm(){
        Intent intent = new Intent(this,MainActivity3.class);
        startActivity(intent);
    }

}