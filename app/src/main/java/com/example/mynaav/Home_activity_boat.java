package com.example.mynaav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Home_activity_boat extends AppCompatActivity {

    EditText phoneno;
    Button Proceedbtn;
    String exist;
    SharedPreferences sharedPreferencesboat;
    private static final String SHARED_PREF_NAME="myprefboat";
    private static final String KEY_NO="mobilenoboat";
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home_boat);


        sharedPreferencesboat=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String mobileno=sharedPreferencesboat.getString(KEY_NO,null);
        if(mobileno!=null){
            Intent intent=new Intent(Home_activity_boat.this,Boatownerwelcomepage.class);
            startActivity(intent);
        }


        exist="0";
        String url ="https://mynaavproject.000webhostapp.com/retreiveboatownerdata.php";




        phoneno=findViewById(R.id.phoneno1);
        Proceedbtn=findViewById(R.id.VERIFY1);
        progressBar=findViewById(R.id.progressBar1);

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
                                Home_activity_boat.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        Toast.makeText(Home_activity_boat.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        Intent intent= new Intent(getApplicationContext(), otp_fetch_boat.class);
                                        intent.putExtra("mobile", phoneno.getText().toString());
                                        intent.putExtra("backendotp", backendotp);
                                        intent.putExtra("boatexist", exist);
                                        startActivity(intent);

                                    }
                                }
                        );


//
                    }
                    else{
                        Toast.makeText(Home_activity_boat.this, "Please Enter Correct Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Home_activity_boat.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
    private void getJSON(final String urlWebService) {
        class GetJSON extends AsyncTask<Void, Void, String> {

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //this method will be called after execution
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {



                try {
                    //creating a URL
                    URL url = new URL(urlWebService);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        sb.append(json + "\n");
                    }

                    //finally returning the read string
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
        //creating a json array from the json string
        JSONArray jsonArray = new JSONArray(json);

        for (int i = 0; i < jsonArray.length(); i++) {

            //getting json object from the json array
            JSONObject obj = jsonArray.getJSONObject(i);
            String temp1 = obj.getString("Address1");
            String temp2 = obj.getString("Validate");
            if(temp1.equals(phoneno.getText().toString())){
                exist="1";
                if(temp2.equals("YES")){
                    exist="2";
                }
            }


        }
    }

}