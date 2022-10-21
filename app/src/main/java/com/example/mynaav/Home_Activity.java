package com.example.mynaav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;

public class Home_Activity extends AppCompatActivity {

    EditText phoneno;
    Button Proceedbtn, demo;
    TextView textview;
    String url, exist;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="mypref";
    private static final String KEY_NO="mobileno";
    ProgressBar progressBar;







    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String mobileno=sharedPreferences.getString(KEY_NO,null);
        if(mobileno!=null){
            Intent intent=new Intent(Home_Activity.this,userboatview.class);
            startActivity(intent);
        }


        exist="0";
        url ="https://mynaavproject.000webhostapp.com/retreiveuserdata.php";
        //phoneno yha se lo
        phoneno=findViewById(R.id.phoneno);

        Proceedbtn=findViewById(R.id.VERIFY);
        textview = findViewById(R.id.BoatOwner);
        progressBar=findViewById(R.id.progressBar1);


        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Home_activity_boat.class);
                startActivity(intent);
            }
        });


        Proceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Proceedbtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);


                getJSON(url);
                if(!phoneno.getText().toString().trim().isEmpty()){
                    if((phoneno.getText().toString().trim()).length()==10){

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + phoneno.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                Home_Activity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        Toast.makeText(Home_Activity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {


                                        Intent intent= new Intent(getApplicationContext(), otpfetch.class);
                                        intent.putExtra("mobile", phoneno.getText().toString());
                                        intent.putExtra("backendotp", backendotp);
                                        intent.putExtra("userexist", exist);

                                        startActivity(intent);

                                    }
                                }
                        );


//
                    }
                    else{
                        Toast.makeText(Home_Activity.this, "Please Enter Correct Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Home_Activity.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }


            }
        });




    }
    private void getJSON(final String urlWebService) {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(Void... voids) {



                try {
                    URL url = new URL(urlWebService);

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    StringBuilder sb = new StringBuilder();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;

                    while ((json = bufferedReader.readLine()) != null) {

                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }

        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject obj = jsonArray.getJSONObject(i);
            String temp =  obj.getString("phoneno");
            if(temp.equals(phoneno.getText().toString())){
                exist= "1";
            }
        }
    }
}