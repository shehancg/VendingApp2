
package com.example.vendingapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class admin1 extends AppCompatActivity {

    private EditText editText;
    private Button btn,testbtn,backbtn;
    private TextView txtvw;
    public int totalbottles;
    public String totalbottles1;
    private String txt;
    public String batLevel;
    public String totalBottel;

    EditText bottelCount;

    // Bluetooth
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket3 = null;
    private boolean isBTConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public String address = "98:D3:11:FC:31:AD";

    private BroadcastReceiver batteryreceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent){
            int level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);


            batLevel = String.valueOf(level);
            Log.d(TAG,"battery level : "+batLevel.toString());

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin1);
        btn = (Button) findViewById(R.id.button);
        testbtn = (Button) findViewById(R.id.button10);

        backbtn = (Button) findViewById(R.id.button2);

        editText = (EditText) findViewById(R.id.editTextNumber);
        txtvw = (TextView) findViewById(R.id.textView6);

        new ConnectBT().execute(); // Connection class
        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
                getBottel();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reDirect();

            }
        });

        this.registerReceiver(this.batteryreceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        totalBottel = getIntent().getStringExtra("totalBottel");
        Log.d(TAG,"total bottel : "+totalBottel);
        txtvw.setText(totalBottel.toString());





    }
    //test bottel
    public void getBottel(){
        background5 bg = new background5(this);
        bg.execute("0",batLevel);

    }
    public void reDirect(){
        Intent intentMain = new Intent(this,MainActivity.class);
        startActivity(intentMain);

    }
    public void setBtn(View view) {
        txt = editText.getText().toString();
        //txt!=null||txt!=""
        if(txt.length()!=0){

            totalbottles = Integer.parseInt(txt) + Integer.parseInt(totalBottel);

            if(totalbottles <= 40){
                totalBottel = String.valueOf(totalbottles);
                // totalbottles = Integer.parseInt(txt) + Integer.parseInt(totalBottel);
                totalbottles1 = String.valueOf(totalbottles);
                txtvw.setText(totalbottles1.toString());
                Log.d(TAG,"bottel count : "+txt.toString());
                background2 bg = new background2(this);
                bg.execute(txt,batLevel);
            }else{

            }

        }
        else{

        }



    }
    private void send() {
        if (btSocket3!=null) {
            try {
                btSocket3.getOutputStream().write(65);


                // Send data to bt module: 255 (on) [max attainable value]

            }
            catch (IOException e) {
                toast("Error Sending Data");
            }
        }
    }
    private void disconnect() {
        if (btSocket3 != null) { // If bluetooth socket is taken then disconnect
            try {
                btSocket3.close(); // Close bluetooth connection
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
            progress = ProgressDialog.show(admin1.this, "Connecting...", "Please wait!");  // Connection loading dialog
        }

        @Override
        protected Void doInBackground(Void... devices) { // Connect with bluetooth socket

            try {
                if (btSocket3 == null || !isBTConnected) { // If socket is not taken or device not connected
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice device = myBluetooth.getRemoteDevice(address); // Connect to the chosen MAC address
                    btSocket3 = device.createInsecureRfcommSocketToServiceRecord(myUUID); // This connection is not secure (mitm attacks)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery(); // Discovery process is heavy
                    btSocket3.connect();
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