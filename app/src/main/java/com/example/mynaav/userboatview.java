package com.example.mynaav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class userboatview extends AppCompatActivity {
    ListView listView;
    String phoneuser;
    EditText Date, Time;
    Spinner BoatSize, BoatStand;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userboatview);
        phoneuser= getIntent().getStringExtra("phoneno");
        listView=findViewById(R.id.boatdata);
        BoatSize=findViewById(R.id.selectboatsize);
        BoatStand=findViewById(R.id.selectghat);
        button=findViewById(R.id.enquireboatbutton);

        Spinner myspinner = findViewById(R.id.selectghat);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(userboatview.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.BoatStand));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(adapter);

        Spinner myspinner2= findViewById(R.id.selectboatsize);
        ArrayAdapter<String> adapter1= new ArrayAdapter<String>(userboatview.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.BoatSize));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner2.setAdapter(adapter1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AvailableBoats.class);
                intent.putExtra("BoatS", BoatSize.getSelectedItem().toString());
                intent.putExtra("BoatSt", BoatStand.getSelectedItem().toString());
                intent.putExtra("p", phoneuser);
                startActivity(intent);

            }
        });




    }


}