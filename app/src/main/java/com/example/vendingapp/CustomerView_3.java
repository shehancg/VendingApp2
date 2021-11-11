package com.example.vendingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class CustomerView_3 extends AppCompatActivity {
    private TextToSpeech tts;
    private String txt="Congratulations ! Enjoy your drink in a clear bottle, which is now more recyclable. Thank you!";
    public int btlevel;
    public String batLevel;

    // Bluetooth
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBTConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public String address = "98:D3:11:FC:31:AD";// MAC address machine
    //public String address = "3C:F8:62:C6:67:B7";// MAC address kasun

    private BroadcastReceiver batteryreceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent){
            btlevel =intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_3);
        this.registerReceiver(this.batteryreceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        new ConnectBT().execute(); // Connection class

        tts = new TextToSpeech(getApplicationContext(), status -> {
            if(status == TextToSpeech.SUCCESS ){
                //select Language
                tts.setLanguage(Locale.ENGLISH);
                tts.setSpeechRate(1);
                tts.setPitch(1);
                tts.speak(txt,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        final Handler handler = new Handler();
        final Handler handler2 = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                send();

            }
        }, 5000);
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                disconnect();
                redirrecttoMain();

            }
        }, 12000);
    }
    private void redirrecttoMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void send() {
        if (btSocket!=null) {
            try {
                btSocket.getOutputStream().write(65);
                if(btlevel == 100){
                    btSocket.getOutputStream().write(72);
                }else if(btlevel<=25){
                    btSocket.getOutputStream().write(76);
                }

                // Send data to bt module: 255 (on) [max attainable value]

            }
            catch (IOException e) {
                toast("Error Sending Data");
            }
        }
    }
    private void disconnect() {
        if (btSocket != null) { // If bluetooth socket is taken then disconnect
            try {
                btSocket.close(); // Close bluetooth connection
            }
            catch (IOException e) {
                toast("Error Closing Socket");
            }
        }
        finish();
    }
    private void toast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> { // UI thread

        private boolean connectionSuccess = true;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(CustomerView_3.this, "Connecting...", "Please wait!");  // Connection loading dialog
        }

        @Override
        protected Void doInBackground(Void... devices) { // Connect with bluetooth socket

            try {
                if (btSocket == null || !isBTConnected) { // If socket is not taken or device not connected
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice device = myBluetooth.getRemoteDevice(address); // Connect to the chosen MAC address
                    btSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID); // This connection is not secure (mitm attacks)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery(); // Discovery process is heavy
                    btSocket.connect();
                }
            }
            catch (IOException e) {
                connectionSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) { // After doInBackground
            super.onPostExecute(result);

            if (!connectionSuccess) {
                toast("Connection Failed. Try again.");
                finish();
            }
            else {
                toast(" Connected. ");
                isBTConnected = true;
            }
            progress.dismiss();
        }
    }

}