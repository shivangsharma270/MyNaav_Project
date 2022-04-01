package com.example.mynaav;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;

public class otpfetch extends AppCompatActivity {
    EditText otp_edit_box1,otp_edit_box2,otp_edit_box3,otp_edit_box4,otp_edit_box5,otp_edit_box6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_otpfetch);
        otp_edit_box1=findViewById(R.id.otp_edit_box1);
        otp_edit_box2=findViewById(R.id.otp_edit_box2);
        otp_edit_box3=findViewById(R.id.otp_edit_box3);
        otp_edit_box4=findViewById(R.id.otp_edit_box4);
        otp_edit_box5=findViewById(R.id.otp_edit_box5);
        otp_edit_box6=findViewById(R.id.otp_edit_box6);

        otp_edit_box1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp_edit_box1.getText().toString().length()==1){
                    otp_edit_box2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        otp_edit_box2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp_edit_box2.getText().toString().length()==1){
                    otp_edit_box3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(otp_edit_box2.getText().toString().length()==0){
                    otp_edit_box1.requestFocus();
                }
            }
        });

        otp_edit_box3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp_edit_box3.getText().toString().length()==1){
                    otp_edit_box4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(otp_edit_box3.getText().toString().length()==0){
                    otp_edit_box2.requestFocus();
                }
            }
        });

        otp_edit_box4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp_edit_box4.getText().toString().length()==1){
                    otp_edit_box5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(otp_edit_box4.getText().toString().length()==0){
                    otp_edit_box3.requestFocus();
                }
            }
        });

        otp_edit_box5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp_edit_box5.getText().toString().length()==1){
                    otp_edit_box6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(otp_edit_box5.getText().toString().length()==0){
                    otp_edit_box4.requestFocus();
                }
            }
        });


        otp_edit_box6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp_edit_box6.getText().toString().length()==1){
                    otp_edit_box6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(otp_edit_box6.getText().toString().length()==0){
                    otp_edit_box5.requestFocus();
                }
            }
        });

    }
}