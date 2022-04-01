package com.example.mynaav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class Home_Activity extends AppCompatActivity {

    EditText phoneno;
    Button Proceedbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        phoneno=findViewById(R.id.phoneno);
        Proceedbtn=findViewById(R.id.Proceedbtn);


        Proceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phonenumber=phoneno.getText().toString();

                boolean check=validateinfo(phonenumber);

            }
        });




    }


    private Boolean validateinfo(String phonenumber) {
        if (phonenumber.length() < 10 || phonenumber.length() > 10) {
            phoneno.requestFocus();
            phoneno.setError("Invalid Number");
            return true;
        } else {
            Intent intent = new Intent(Home_Activity.this, otpfetch.class);
            startActivity(intent);
            return false;
        }
    }
}