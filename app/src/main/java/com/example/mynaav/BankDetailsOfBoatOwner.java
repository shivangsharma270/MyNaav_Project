package com.example.mynaav;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

public class BankDetailsOfBoatOwner extends AppCompatActivity {
    EditText FullName, Age, EmailId, LicenseNo, Address1, Address2, Pincode;
    Spinner BoatSize, BoatStand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details_of_boat_owner);


    }
}