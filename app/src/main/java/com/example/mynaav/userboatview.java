package com.example.mynaav;

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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.*;
import java.math.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class userboatview extends AppCompatActivity {
    ListView listView;
    String phoneuser, s1,s2;
    EditText Date, Time;
    Spinner BoatSize, BoatStand;
    Button button;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="mypref";
    private static final String KEY_NO="mobileno";
    int counter=0;
    @Override
    public void onBackPressed(){
        counter++;
        if(counter==1){
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        }
        else if(counter==2){
            finishAffinity();
        }



    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_userboatview);

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);


        String phonenoofsp=sharedPreferences.getString(KEY_NO,null);


        phoneuser= phonenoofsp;
        listView=findViewById(R.id.boatdata);
        BoatSize=findViewById(R.id.selectboatsize);
        BoatStand=findViewById(R.id.selectghat);
        button=findViewById(R.id.enquireboatbutton);
        String url="https://mynaavproject.000webhostapp.com/retreiveboatownerdata.php";


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
                getJSON(url);
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

        //creating a string array for listview

        //looping through all the elements in json array
        int min=0;
        int max=0;


        for (int i = 0; i < jsonArray.length(); i++) {


            JSONObject obj = jsonArray.getJSONObject(i);
            String temp1= obj.getString("BoatSize");
            String temp2= obj.getString("BoatStand");
            String temp3= obj.getString("Validate");

            if(temp1.equals(BoatSize.getSelectedItem().toString())){
                if(temp2.equals(BoatStand.getSelectedItem().toString())){
                    if(temp3.equals("YES")) {
                            max++;
                    }
                }
            }
            //getting the name from the json object and putting it inside string array
        }
        int range= max-min+1;
        int rand = (int)(Math.random()* range)+min;
        String[] arr =new String[max];
        String[] arr1 =new String[max];
        int count=0;
        int count1=0;
        for (int i = 0; i < jsonArray.length(); i++) {


            JSONObject obj = jsonArray.getJSONObject(i);
            String temp1= obj.getString("BoatSize");
            String temp2= obj.getString("BoatStand");
            String temp3= obj.getString("Validate");

            if(temp1.equals(BoatSize.getSelectedItem().toString())){
                if(temp2.equals(BoatStand.getSelectedItem().toString())){
                    if(temp3.equals("YES")) {
                        arr[count++]= obj.getString("FullName");
                        arr1[count1++]= obj.getString("Address1");

                    }
                }
            }
            //getting the name from the json object and putting it inside string array
        }
        s1=arr[rand];
        s2=arr1[rand];
        Intent intent = new Intent(getApplicationContext(), AvailableBoats.class);
        intent.putExtra("s1", s1);
        intent.putExtra("s2", s2);
        intent.putExtra("p", phoneuser);
        startActivity(intent);
    }



}