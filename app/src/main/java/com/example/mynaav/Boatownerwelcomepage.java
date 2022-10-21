package com.example.mynaav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import android.widget.Toast;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.*;
import java.util.ArrayList;

public class Boatownerwelcomepage extends AppCompatActivity {
    String boatid , exist, name, phonen;
    TextView text;
    ListView l;
    long res;
    SharedPreferences sharedPreferencesboat;
    private static final String SHARED_PREF_NAMEboat="myprefboat";
    private static final String KEY_NOboat="mobilenoboat";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_boatownerwelcomepage);
        sharedPreferencesboat=getSharedPreferences(SHARED_PREF_NAMEboat,MODE_PRIVATE);
        String boat=sharedPreferencesboat.getString(KEY_NOboat,null);
        exist="NO RIDES NOW";
        name="NO USER";
        l= findViewById(R.id.list);
        boatid = getIntent().getStringExtra("boatid");
        phonen="NO DETAILS TO DISPLAY";
        String url="https://mynaavproject.000webhostapp.com/consumedboatfetch.php";
        getJSON(url);
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

        ArrayList<String> list=new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {


            JSONObject obj = jsonArray.getJSONObject(i);
            String temp1= obj.getString("BoatID");
            String temp2=obj.getString("UserID");
            String temp3= obj.getString("extra");
            String temp4= obj.getString("paymentid");
            //res = Long.parseLong(temp4);

            if(temp1.equals(boatid)){
                String s= "UserID: "+temp2+"\n"+"Date and Time: "+temp3+"\n"+ "PaymentID: "+temp4;
                list.add(s);
            }


            //getting the name from the json object and putting it inside string array
        }
        Collections.reverse(list);
        final ArrayAdapter<String > adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        l.setAdapter(adapter);

    }

//    private void getJSON1(final String urlWebService) {
//        class GetJSON extends AsyncTask<Void, Void, String> {
//
//            //this method will be called before execution
//            //you can display a progress bar or something
//            //so that user can understand that he should wait
//            //as network operation may take some time
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }
//
//            //this method will be called after execution
//            //so here we are displaying a toast with the json string
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                try {
//                    loadIntoListView1(s);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            //in this method we are fetching the json string
//            @Override
//            protected String doInBackground(Void... voids) {
//
//
//
//                try {
//                    //creating a URL
//                    URL url = new URL(urlWebService);
//
//                    //Opening the URL using HttpURLConnection
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
//                    //StringBuilder object to read the string from the service
//                    StringBuilder sb = new StringBuilder();
//
//                    //We will use a buffered reader to read the string from service
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//                    //A simple string to read values from each line
//                    String json;
//
//                    //reading until we don't find null
//                    while ((json = bufferedReader.readLine()) != null) {
//
//                        //appending it to string builder
//                        sb.append(json + "\n");
//                    }
//
//                    //finally returning the read string
//                    return sb.toString().trim();
//                } catch (Exception e) {
//                    return null;
//                }
//
//            }
//        }
//
//        GetJSON getJSON = new GetJSON();
//        getJSON.execute();
//    }
//    private void loadIntoListView1(String json) throws JSONException {
//        //creating a json array from the json string
//        JSONArray jsonArray = new JSONArray(json);
//
//        //creating a string array for listview
//
//        //looping through all the elements in json array
//        for (int i = 0; i < jsonArray.length(); i++) {
//
//
//            JSONObject obj = jsonArray.getJSONObject(i);
//            String temp1= obj.getString("phoneno");
//            String temp2=obj.getString("fullname");
//            if(temp1.equals(exist)){
//                name=temp2;
//                phonen=temp1;
//                Toast.makeText(this, "Your ride", Toast.LENGTH_SHORT).show();
//
//            }
//
//
//            //getting the name from the json object and putting it inside string array
//        }
//
//        final Handler handler = new Handler();
//        Runnable refresh = new Runnable() {
//            @Override
//            public void run() {
//                // data request
//                handler.postDelayed(this, 1000);
//            }
//        };
//        handler.postDelayed(refresh, 1000);
//    }
}