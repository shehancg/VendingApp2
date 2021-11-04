package com.example.vendingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    String pno;
    Boolean vflag,result;
    private Button go;
    private EditText txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt =(EditText) findViewById(R.id.editTextPhone);
        go =(Button) findViewById(R.id.buttonenter);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pno = txt.getText().toString();
                vflag = validate();

                if(vflag.equals(true)) {

                    if(pno.equals("0000")){

                        openadmin();

                    }else {

                        openuser();

                    }
                }
            }
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