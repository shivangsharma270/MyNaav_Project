package com.example.mynaav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

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
        Proceedbtn=findViewById(R.id.VERIFY);
        Proceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phonenumber=phoneno.getText().toString();

                boolean check=validateinfo(phonenumber);
                Proceedbtn.setVisibility(View.INVISIBLE);
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + phoneno.getText().toString(), 60, TimeUnit.SECONDS,
                        Home_Activity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Proceedbtn.setVisibility(View.VISIBLE);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Proceedbtn.setVisibility(View.VISIBLE);
                                Toast.makeText(Home_Activity.this,"Please check your internet connection",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String backotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(backotp, forceResendingToken);
                                Intent intent = new Intent(Home_Activity.this, otpfetch.class);
                                intent.putExtra("mobile",phoneno.getText().toString());
                                intent.putExtra("backotp",backotp);
                                startActivity(intent);
                            }
                        });

            }
        });




    }


    private Boolean validateinfo(String phonenumber) {
        if (phonenumber.length() < 10 || phonenumber.length() > 10) {
            phoneno.requestFocus();
            phoneno.setError("Invalid Number");
            return true;
        } else {





            return false;
        }
    }
}