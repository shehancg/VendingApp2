package com.example.vendingapp;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
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
import java.util.concurrent.TimeUnit;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class CustomerView_2 extends AppCompatActivity {

    protected static final int RESULT_SPEECH = 1;
    private TextToSpeech tts;
    private Intent intent;
    private SpeechRecognizer speechRecognizer;
    public String phoneNo1 ;
    public String batLevel;
    public int attempt=0;
    public boolean cuntdwn = true;
    CountDownTimer cdt;


    TextView tvcd,errprompt;

    private BroadcastReceiver batteryreceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent){
            int level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            // battery.setText(String.valueOf(level));

            batLevel = String.valueOf(level);
            Log.d(TAG,"battery level : "+batLevel.toString());

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_2);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);
        ImageButton btnspeak = findViewById(R.id.btn_speak);
        String txt = "Thank You! What would you like to say ?";
        errprompt = (TextView) findViewById(R.id.textView12);
        this.registerReceiver(this.batteryreceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        phoneNo1 = getIntent().getStringExtra("getPhoneN");

        tvcd = (TextView) findViewById(R.id.textView7);


        tts = new TextToSpeech(getApplicationContext(), status -> {
            if(status == TextToSpeech.SUCCESS ){
                //select Language

                tts.setLanguage(Locale.ENGLISH);
                tts.setSpeechRate(1);
                tts.setPitch(1);
                tts.speak(txt,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        long duration = TimeUnit.MINUTES.toMillis(1);
        cdt = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l) {
                String sDuration = String.format(Locale.ENGLISH, "%02d:%02d"
                        ,TimeUnit.MILLISECONDS.toMinutes(l)
                        ,TimeUnit.MILLISECONDS.toSeconds(l) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                tvcd.setText(sDuration);


            }

            @Override
            public void onFinish() {
                /*if(cuntdwn==true){
                    redirrecttoMain();
                }*/
                redirrecttoMain();
            }
        };
        cdt.start();
        attempt = 0;
        btnspeak.setOnClickListener(v -> {
            attempt = attempt+1;
            errprompt.setVisibility(View.INVISIBLE);;
            if(attempt==4){
                cdt.cancel();
                redirrecttoMain();
            }

            if(attempt <=3){
                voiceautomation();}


        });



    }
    private void redirrecttoMain(){
        Intent intent = new Intent(this, tryagain.class);
        startActivity(intent);
    }
    private void voiceautomation(){
        Intent voice = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en-Uk");
        voice.putExtra(RecognizerIntent.EXTRA_PROMPT,"What would you like to say ?");
        startActivityForResult(voice,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            ArrayList<String> arrayList=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(arrayList.get(0).toString().equals("Sprite new look same great taste")){
                cdt.cancel();
                background bg = new background(this);
                bg.execute(phoneNo1,batLevel);
            }else{
                errprompt.setVisibility(View.VISIBLE);;
                errprompt.setText("Sorry ! Try again. ");
            }
        }
    }
}