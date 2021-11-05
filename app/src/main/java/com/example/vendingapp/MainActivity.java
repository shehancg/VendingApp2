package com.example.vendingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0;
    ImageButton back;
    String pno;
    Boolean vflag,result;
    private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.textView3);
        ImageButton go = findViewById(R.id.btnen);
        go.setOnClickListener(v -> {
            pno = txt.getText().toString();
            vflag = validate();

            if(vflag.equals(true)) {

                if(pno.equals("0000")){

                    openadmin();

                }else {

                    openuser();

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

        back.setOnClickListener(view -> {
            StringBuilder stringBuilder=new StringBuilder(txt.getText());
            stringBuilder.deleteCharAt(txt.getText().length()-1);
            String newstring=stringBuilder.toString();
            txt.setText(newstring);
        });

    }
    private boolean validate(){

        if(pno.length()==0){

            txt.requestFocus();
            txt.setError("Please Enter Your Mobile Number");
            result = false;

        }else if(pno.length() == 10||pno.length() == 4){

            result = true;
        }
        return result;
    }
    private void openuser(){

        Intent intent = new Intent(this, CustomerView1.class);
        startActivity(intent);
    }
    private void openadmin(){

        Intent intent = new Intent(this, Admin.class);
        startActivity(intent);
    }
}