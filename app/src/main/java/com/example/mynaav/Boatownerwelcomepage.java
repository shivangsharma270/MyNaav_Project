package com.example.mynaav;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
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

public class Boatownerwelcomepage extends AppCompatActivity {
    String boatid, exist, name, phonen;
    TextView text,text2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boatownerwelcomepage);
        exist="NO RIDES NOW";
        text=findViewById(R.id.text1);
        text2=findViewById(R.id.text2);
        boatid=getIntent().getStringExtra("boatid");
        name="NO USER";
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

            //this method will be called after execution
            //so here we are displaying a toast with the json string
            @RequiresApi(api = Build.VERSION_CODES.O)
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadIntoListView(String json) throws JSONException {
        //creating a json array from the json string
        JSONArray jsonArray = new JSONArray(json);

        //creating a string array for listview

        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {


            JSONObject obj = jsonArray.getJSONObject(i);
            String temp1= obj.getString("BoatID");
            String temp2=obj.getString("UserID");
            String temp3= obj.getString("onlydate");
            String temp4= obj.getString("onlytime");
            long result = Long.parseLong(temp4);

            if((Math.abs(System.currentTimeMillis()-result)<600000) && (temp3.equals((java.time.LocalDate.now()).toString()))){
                if(temp1.equals(boatid)){
                    exist=temp2;
                    Toast.makeText(this, "Your ride", Toast.LENGTH_SHORT).show();
                }
            }

            //getting the name from the json object and putting it inside string array
        }
        String url="https://mynaavproject.000webhostapp.com/retreiveuserdata.php";
        getJSON1(url);

    }

    private void getJSON1(final String urlWebService) {
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
                    loadIntoListView1(s);
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
    private void loadIntoListView1(String json) throws JSONException {
        //creating a json array from the json string
        JSONArray jsonArray = new JSONArray(json);

        //creating a string array for listview

        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {


            JSONObject obj = jsonArray.getJSONObject(i);
            String temp1= obj.getString("phoneno");
            String temp2=obj.getString("fullname");
            if(temp1.equals(exist)){
                name=temp2;
                phonen=temp1;
                Toast.makeText(this, "Your ride", Toast.LENGTH_SHORT).show();

            }


            //getting the name from the json object and putting it inside string array
        }
        text.setText(name);
        text2.setText(phonen);
        final Handler handler = new Handler();
        Runnable refresh = new Runnable() {
            @Override
            public void run() {
                // data request
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(refresh, 1000);
    }
}