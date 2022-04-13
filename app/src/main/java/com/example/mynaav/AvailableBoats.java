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
import com.android.volley.toolbox.Volley;

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
    private RequestQueue requestQueue1;
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

        s1=getIntent().getStringExtra("s1");
        s2=getIntent().getStringExtra("s2");
        BoatStand=getIntent().getStringExtra("BoatSt");
        listView.setText(s1);
        listView1.setText(s2);
        requestQueue1= Volley.newRequestQueue(getApplicationContext());
        sendData();
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
                    Intent intent= new Intent(getApplicationContext(),userboatview.class);
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
        requestQueue1.add(request);
    }
}