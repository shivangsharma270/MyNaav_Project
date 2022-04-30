package com.example.mynaav;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences sharedPreferencesboat;

    private static final String SHARED_PREF_NAME="myprefboat";
    private static final String KEY_NO="mobilenoboat";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boatownerwelcomepage);

        String mobileno=sharedPreferencesboat.getString(KEY_NO,null);



    }


}