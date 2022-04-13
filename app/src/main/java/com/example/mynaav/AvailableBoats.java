package com.example.mynaav;

import androidx.appcompat.app.AppCompatActivity;
import java.lang.*;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.*;
import java.util.HashMap;
import java.util.Map;

public class AvailableBoats extends AppCompatActivity {
    String BoatSize, BoatStand, s1, s2, phoneuser;
    TextView listView, listView1;
    private String sendurl = "https://mynaavproject.000webhostapp.com/consumedboatpush.php";
    private RequestQueue requestQueue;
    private static final String TAG=UserData.class.getSimpleName();
    int success;
    private String TAG_SUCCESS="success";
    private String TAG_MESSAGE="message";
    private String tag_json_obj="json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_boats);
        phoneuser= getIntent().getStringExtra("p");
        BoatSize=getIntent().getStringExtra("BoatS");
        listView= findViewById(R.id.availableboatsinfo);
        listView1= findViewById(R.id.availableboatsinfo3);

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

        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {


            JSONObject obj = jsonArray.getJSONObject(i);
            String temp1= obj.getString("BoatSize");
            String temp2= obj.getString("BoatStand");
            String temp3= obj.getString("Validate");
            String temp4= obj.getString("Available");

            if(BoatSize.equals(temp1)){
                if(BoatStand.equals(temp2)){
                    if(temp3.equals("YES")) {
                        if(temp4.equals("YES")){

                            s1 = obj.getString("FullName");
                            s2 = obj.getString("Address1");
                            listView.setText(s1);
                            listView1.setText(s2);
                            sendData();
                            break;
                        }
                    }
                }
            }
            //getting the name from the json object and putting it inside string array
        }
    }


    public void sendData(){
        StringRequest request=new StringRequest(Request.Method.POST, sendurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    success = jobj.getInt(TAG_SUCCESS);
                    if (success == 1)
                        Toast.makeText(AvailableBoats.this, jobj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(AvailableBoats.this, jobj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(AvailableBoats.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(),AvailableBoats.class);
                    startActivity(intent);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AvailableBoats.this,error.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }

        }){

            public Map<String,String> getParams(){
                String temp="exist";
                Map<String,String> params=new HashMap<String,String>();
                params.put("UserID", phoneuser);
                params.put("BoatID", s2);
                params.put("extra", temp);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(request);
    }
}