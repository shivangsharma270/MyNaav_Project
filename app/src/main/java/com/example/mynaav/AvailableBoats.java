package com.example.mynaav;

import androidx.appcompat.app.AppCompatActivity;
import java.lang.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AvailableBoats extends AppCompatActivity {
    String BoatSize, BoatStand;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_boats);

        BoatSize=getIntent().getStringExtra("BoatS");
        listView= findViewById(R.id.availableboatsinfo);
        BoatStand=getIntent().getStringExtra("BoatSt");
        String url="https://mynaavproject.000webhostapp.com/retreiveboatownerdata.php";
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

        //creating a string array for listview

        int j=0;
        for (int i = 0; i < jsonArray.length(); i++) {

            //getting json object from the json array

            JSONObject obj = jsonArray.getJSONObject(i);
            String temp1= obj.getString("BoatSize");
            String temp2= obj.getString("BoatStand");

            if(BoatSize.equals(temp1)){
                if(BoatStand.equals(temp2)){
                    j++;
                }

            }
            //getting the name from the json object and putting it inside string array

        }
        String[] hero = new String[j];
        int i1=0;

        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {

            //getting json object from the json array

            JSONObject obj = jsonArray.getJSONObject(i);
            String temp1= obj.getString("BoatSize");
            String temp2= obj.getString("BoatStand");

            if(BoatSize.equals(temp1)){
                if(BoatStand.equals(temp2)){
                    String s1= obj.getString("FullName");
                    String s2= obj.getString("Address1");
                  hero[i1] = "Name: "+s1+ "      PhoneNo: " + s2;
                  i1++;
                }
            }
            //getting the name from the json object and putting it inside string array

        }

        //the array adapter to load data into list
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(AvailableBoats.this, android.R.layout.simple_list_item_1, hero);

        //attaching adapter to listview
        listView.setAdapter(arrayAdapter1);
    }
}