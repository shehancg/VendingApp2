package com.example.vendingapp;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Locale;

public class CustomerView_2 extends AppCompatActivity {

    protected static final int RESULT_SPEECH = 1;
    private TextToSpeech tts;
    private Intent intent;
    private SpeechRecognizer speechRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_2);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);
        ImageButton btnspeak = findViewById(R.id.btn_speak);
        String txt = "Please say : Sprite new look, same great taste.";

        tts = new TextToSpeech(getApplicationContext(), status -> {
            if(status == TextToSpeech.SUCCESS ){
                //select Language

                tts.setLanguage(Locale.ENGLISH);
                tts.setSpeechRate(0.8f);
                tts.setPitch(1);
                tts.speak(txt,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        btnspeak.setOnClickListener(v -> {
            voiceautomation();
        });
    }
    private void voiceautomation(){
        Intent voice = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en-Uk");
        voice.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say, Sprite new look same great taste");
        startActivityForResult(voice,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            ArrayList<String> arrayList=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(arrayList.get(0).toString().equals("Sprite new look same great taste")){
                Intent intent= new Intent(CustomerView_2.this,CustomerView_3.class);
                startActivity(intent);
            }
        }
    }
}