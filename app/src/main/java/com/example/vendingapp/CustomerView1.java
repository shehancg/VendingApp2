package com.example.vendingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;

import java.util.Locale;

public class CustomerView1 extends AppCompatActivity {
    private TextToSpeech tts;

    private String txt="Please dispose your Empty Plastic Bottle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view1);
        Button nxt = findViewById(R.id.btnGO);

        nxt.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerView1.this,CustomerView_2.class);
            startActivity(intent);
        });

        tts = new TextToSpeech(getApplicationContext(), status -> {
            if(status == TextToSpeech.SUCCESS ){
                tts.setLanguage(Locale.ENGLISH);
                tts.setSpeechRate(0.8f);
                tts.setPitch(1);
                tts.speak(txt,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

    }
}