package com.example.vendingapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.MotionEffect;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class MainActivity2 extends AppCompatActivity {

    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,btnen;
    ImageButton back;
    String pno;
    Boolean vflag,result;
    ArrayList<String> stringArrayList = new ArrayList<String>();
    private TextView txt,errlbl;
    public MediaPlayer mp;
    //Blutooth
    BluetoothAdapter mbluadptr;
    //Create a BrodcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mBcReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //When discovery finds a device
            if(action.equals(mbluadptr.ACTION_STATE_CHANGED)){
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,mbluadptr.ERROR);

                switch (state){
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG,"onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG,"mBcReceiver1: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG,"mBcReceiver1: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG,"mBcReceiver1: STATE Turning on");
                }

            }
        }
    };
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mBcReceiver1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txt = findViewById(R.id.textView3);
        Button go = findViewById(R.id.btnen);
        errlbl = (TextView) findViewById(R.id.textView10);
        //Blutooth
        mbluadptr = BluetoothAdapter.getDefaultAdapter();
        //function for enablebt
        enableBt();
        Log.d(TAG,"enabling bluetooth....");
        //findingpaireddevices();

        mp = MediaPlayer.create(MainActivity2.this,R.raw.bgmusic);
        mp.setLooping(true);
        mp.start();

        go.setOnClickListener(v -> {
            pno = txt.getText().toString();
            vflag = validate();

            if(vflag.equals(true)) {

                if(pno.equals("0000")){
                    mp.stop();
                    openadmin();

                }else {
                    mp.stop();
                   // openuser();

                }
            }
        });

        b1=findViewById(R.id.button1);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button3);
        b4=findViewById(R.id.button4);
        b5=findViewById(R.id.button5);
        b6=findViewById(R.id.button6);
        b7=findViewById(R.id.button7);
        b8=findViewById(R.id.button8);
        b9=findViewById(R.id.button9);
        b0=findViewById(R.id.buttonzero);
        back=findViewById(R.id.backspace);

        b1.setOnClickListener(view -> txt.setText(txt.getText().toString()+"1"));

        b2.setOnClickListener(view -> txt.setText(txt.getText().toString()+"2"));

        b3.setOnClickListener(view -> txt.setText(txt.getText().toString()+"3"));

        b4.setOnClickListener(view -> txt.setText(txt.getText().toString()+"4"));

        b5.setOnClickListener(view -> txt.setText(txt.getText().toString()+"5"));

        b6.setOnClickListener(view -> txt.setText(txt.getText().toString()+"6"));

        b7.setOnClickListener(view -> txt.setText(txt.getText().toString()+"7"));

        b8.setOnClickListener(view -> txt.setText(txt.getText().toString()+"8"));

        b9.setOnClickListener(view -> txt.setText(txt.getText().toString()+"9"));

        b0.setOnClickListener(view -> txt.setText(txt.getText().toString()+"0"));

        if(txt != null){
            back.setOnClickListener(view -> {

                StringBuilder stringBuilder=new StringBuilder(txt.getText());
                if((txt.getText().length()-1)>=0){
                    stringBuilder.deleteCharAt(txt.getText().length()-1);
                    String newstring=stringBuilder.toString();
                    txt.setText(newstring);
                }



            });}

    }
    private boolean validate(){

        if(pno.length()==0){

            txt.requestFocus();
            txt.setError("Please Enter Your Mobile Number");
            errlbl.setText("Please Enter Your Mobile Number");
            result = false;

        }else if(pno.length() == 10||pno.equals("0000")){

            result = true;
        }else{
            txt.requestFocus();
            txt.setError("Please Enter valied Mobile Number");
            errlbl.setText("Please Enter valied Mobile Number");
            result = false;
        }
        return result;
    }
    private void openuser(){

        Intent intent = new Intent(this, CustomerView1.class);
        intent.putExtra("getPhoneN",pno);
        startActivity(intent);
    }
    private void openadmin(){
        String txt1="select";
        background4 bg = new background4(this);
        bg.execute(txt1);
    }
    public void enableBt(){
        if(mbluadptr == null){
            Log.d(TAG,"enableDisabledBT:Does not have BT");
        }if(!mbluadptr.isEnabled()){
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBcReceiver1,BTIntent);
        }
        if(mbluadptr.isEnabled()){
            //mbluadptr.disable();

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBcReceiver1,BTIntent);
        }
    }
}