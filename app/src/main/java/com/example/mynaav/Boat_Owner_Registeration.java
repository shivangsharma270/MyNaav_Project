package com.example.mynaav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Boat_Owner_Registeration extends AppCompatActivity {
    Button nextbankdetails;
    EditText fullname;
    EditText Age;
    Spinner boatsize;
    EditText Email;
    EditText Licenseno;
    EditText adress1;
    EditText adress2;
    EditText pincode;
    Spinner Ghat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_boat_owner_registeration);

        nextbankdetails= findViewById(R.id.Nextbankdetails);
        fullname=findViewById(R.id.fullname);
        Age= findViewById(R.id.age);
        boatsize=findViewById(R.id.BoatSize);
        Email= findViewById(R.id.emailid);
        Licenseno= findViewById(R.id.licenseno);
        adress1= findViewById(R.id.addressline1);
        adress2= findViewById(R.id.addressline2);
        pincode= findViewById(R.id.pincode);
        Ghat= findViewById(R.id.BoatStand);

        Spinner myspinner = findViewById(R.id.BoatStand);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(Boat_Owner_Registeration.this,
        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.BoatStand));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(adapter);

        Spinner myspinner2= findViewById(R.id.BoatSize);
        ArrayAdapter<String> adapter1= new ArrayAdapter<String>(Boat_Owner_Registeration.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.BoatSize));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner2.setAdapter(adapter1);

        nextbankdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),BankDetailsOfBoatOwner.class);
                intent.putExtra("FullName", fullname.getText().toString());
                intent.putExtra("Age", Age.getText().toString());
                intent.putExtra("BoatSize", boatsize.getSelectedItem().toString());
                intent.putExtra("EmailId", Email.getText().toString());
                intent.putExtra("LicenseNo", Licenseno.getText().toString());
                intent.putExtra("Address1", adress1.getText().toString());
                intent.putExtra("Address2", adress2.getText().toString());
                intent.putExtra("Pincode", pincode.getText().toString());
                intent.putExtra("BoatStand", Ghat.getSelectedItem().toString());
                startActivity(intent);
            }
        });

    }

}