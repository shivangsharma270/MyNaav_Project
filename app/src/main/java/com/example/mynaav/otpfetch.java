package com.example.mynaav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class otpfetch extends AppCompatActivity {
    EditText otp_edit_box1,otp_edit_box2,otp_edit_box3,otp_edit_box4,otp_edit_box5,otp_edit_box6,phonenumber;
    Button Verifybtn;
    String getotpbackend, phoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_otpfetch);

        Button Verifybtn = findViewById(R.id.Verifybtn);
        phoneno=getIntent().getStringExtra("mobile");
        getotpbackend= getIntent().getStringExtra("backendotp");
        phonenumber=findViewById(R.id.phoneno);
        otp_edit_box1 =findViewById(R.id.otp_edit_box1);
        otp_edit_box2=findViewById(R.id.otp_edit_box2);
        otp_edit_box3=findViewById(R.id.otp_edit_box3);
        otp_edit_box4=findViewById(R.id.otp_edit_box4);
        otp_edit_box5=findViewById(R.id.otp_edit_box5);
        otp_edit_box6=findViewById(R.id.otp_edit_box6);
        //final ProgressBar progressBar=findViewById(R.id.pro)
        Verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!otp_edit_box1.getText().toString().trim().isEmpty()  && !otp_edit_box2.getText().toString().trim().isEmpty()  && !otp_edit_box3.getText().toString().trim().isEmpty()  && !otp_edit_box4.getText().toString().trim().isEmpty()  && !otp_edit_box5.getText().toString().trim().isEmpty()  && !otp_edit_box6.getText().toString().trim().isEmpty() ){
                    String entercodeotp =otp_edit_box1.getText().toString()+
                            otp_edit_box2.getText().toString()+
                            otp_edit_box3.getText().toString()+
                            otp_edit_box4.getText().toString()+
                            otp_edit_box5.getText().toString()+
                            otp_edit_box6.getText().toString();
                    if(getotpbackend!=null){
                        PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(
                                getotpbackend,entercodeotp
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            if(phoneAuthCredential.getProvider().isEmpty()) {
                                                Intent intent = new Intent(getApplicationContext(), UserData.class);
                                                intent.putExtra("Phoneno", phoneno);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }
                                            else{
                                                Intent intent = new Intent(getApplicationContext(), userboatview.class);

                                                startActivity(intent);
                                            }
                                        }
                                        else{
                                            Toast.makeText(otpfetch.this,"Enter Correct OTP", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });


                    }else{
                        Toast.makeText(otpfetch.this,"Please Check your internet connection!!!", Toast.LENGTH_SHORT).show();
                    }


                    //Toast.makeText(otpfetch.this, "Verify OTP", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(otpfetch.this, "Enter full OTP", Toast.LENGTH_SHORT).show();

                }


            }

        });
        numberotpmove();

    }

    private void numberotpmove() {
        otp_edit_box1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!toString().trim().isEmpty()){
                    otp_edit_box2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(otp_edit_box1.getText().toString().length()==0){
                    otp_edit_box1.requestFocus();
                }
            }
        });

        otp_edit_box2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!toString().trim().isEmpty()){
                    otp_edit_box3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(otp_edit_box2.getText().toString().length()==0){
                    otp_edit_box1.requestFocus();
                }
            }
        });

        otp_edit_box3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!toString().trim().isEmpty()){
                    otp_edit_box4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(otp_edit_box3.getText().toString().length()==0){
                    otp_edit_box2.requestFocus();
                }
            }
        });

        otp_edit_box4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!toString().trim().isEmpty()){
                    otp_edit_box5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(otp_edit_box4.getText().toString().length()==0){
                    otp_edit_box3.requestFocus();
                }
            }
        });

        otp_edit_box5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!toString().trim().isEmpty()){
                    otp_edit_box6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(otp_edit_box5.getText().toString().length()==0){
                    otp_edit_box4.requestFocus();
                }
            }
        });

        otp_edit_box6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(otp_edit_box6.getText().toString().length()==0){
                    otp_edit_box5.requestFocus();
                }
            }
        });
    }
}