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
    Button nextbankdetails= (Button) findViewById(R.id.Nextbankdetails);
    EditText fullname=(EditText) findViewById(R.id.fullname);
    EditText Age= (EditText) findViewById(R.id.age);
    Spinner boatsize=(Spinner) findViewById(R.id.BoatSize);
    EditText Email=(EditText) findViewById(R.id.emailid);
    EditText Licenseno=(EditText) findViewById(R.id.licenseno);
    EditText adress1=(EditText) findViewById(R.id.addressline1);
    EditText adress2=(EditText) findViewById(R.id.addressline2);
    EditText pincode=(EditText) findViewById(R.id.pincode);
    Spinner Ghat= (Spinner) findViewById(R.id.BoatStand);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boat_owner_registeration);

        Spinner myspinner= findViewById(R.id.BoatStand);
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
                intent.putExtra("BoatSize", boatsize.toString());
                intent.putExtra("EmailId", Email.getText().toString());
                intent.putExtra("LicenseNo", Licenseno.getText().toString());
                intent.putExtra("Address1", adress1.getText().toString());
                intent.putExtra("Address2", adress2.getText().toString());
                intent.putExtra("Pincode", pincode.getText().toString());
                intent.putExtra("BoatStand", Ghat.toString());
                startActivity(intent);
            }
        });

    }

}