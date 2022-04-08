package com.example.mynaav;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.EditText;
import android.widget.Spinner;

public class BankDetailsOfBoatOwner extends AppCompatActivity {
    String FullName, Age, EmailId, LicenseNo, Address1, Address2, Pincode, BoatSize, BoatStand;
    EditText AccountNo, BankName, IFSCCode,AccountHolderName,UPIid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details_of_boat_owner);
        FullName=getIntent().getStringExtra("FullName");
        Age=getIntent().getStringExtra("Age");
        BoatSize=getIntent().getStringExtra("BoatSize");
        EmailId=getIntent().getStringExtra("EmailId");
        LicenseNo=getIntent().getStringExtra("LicenseNo");
        Address1= getIntent().getStringExtra("Address1");
        Address2= getIntent().getStringExtra("Address2");
        Pincode=getIntent().getStringExtra("Pincode");
        BoatStand= getIntent().getStringExtra("BoatStand");
        AccountNo=findViewById(R.id.AccountNo);
        BankName=findViewById(R.id.BankName);
        IFSCCode=findViewById(R.id.IFSCCode);
        AccountHolderName=findViewById(R.id.AccountHolderName);
        UPIid=findViewById(R.id.UPIid);





    }
}