package com.example.vendingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class admin1 extends AppCompatActivity {

    private EditText editText;
    private Button btn;
    private TextView txtvw;
    public int totalbottles;
    private String txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin1);
        btn = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editTextNumber);
        txtvw = (TextView) findViewById(R.id.textView6);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt = editText.getText().toString();
                if(txt!=null||txt!=""){
                    totalbottles = Integer.parseInt(txt);
                    txtvw.setText(txt.toString());
                }


            }
        });
    }
}