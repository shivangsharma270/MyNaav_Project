package com.example.mynaav;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.*;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AvailableBoats extends AppCompatActivity implements PaymentResultListener {
    String BoatSize, BoatStand, s1, s2, phoneuser;
    TextView listView, listView1,demotextview,demotextview2;
    Button Payment,call,refresh;
    EditText noofriders;
    private String sendurl = "https://mynaavproject.000webhostapp.com/consumedboatpush.php";
    private RequestQueue requestQueue1;
    private static final String TAG=UserData.class.getSimpleName();
    int success;
    private String TAG_SUCCESS="success";
    private String TAG_MESSAGE="message";
    private String tag_json_obj="json_obj_req";
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy G 'at' HH:mm:ss z");
    String currentDateandTime = sdf.format(new Date());

    SimpleDateFormat sdf1=new SimpleDateFormat("dd.MM.yyyy");
    String currentDate=sdf1.format(new Date());

    Date date = new Date();
    long timeMilli = date.getTime();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_available_boats);
        //Toast.makeText(this,currentDate, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), String.valueOf(timeMilli), Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Information!!!");
        builder.setMessage("Call the boat owners before the payment. In case of any payment loss, company is not responsible at all.")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.show();





        phoneuser= getIntent().getStringExtra("p");
        BoatSize=getIntent().getStringExtra("BoatS");
        listView= findViewById(R.id.availableboatsinfo);
        listView1= findViewById(R.id.availableboatsinfo3);
        Payment=findViewById(R.id.proceedforpayment);

        demotextview=findViewById(R.id.demotextview);
        demotextview2=findViewById(R.id.demotextview2);
        demotextview2.setText(String.valueOf(timeMilli));
        listView.requestFocus();
        call=findViewById(R.id.call);
        refresh=findViewById(R.id.refresh);




        s1=getIntent().getStringExtra("s1");
        s2=getIntent().getStringExtra("s2");
        BoatStand=getIntent().getStringExtra("BoatSt");
        listView.setText(s1);
        listView1.setText(s2);
        requestQueue1= Volley.newRequestQueue(getApplicationContext());
        noofriders=findViewById(R.id.noofriders);


        String sAmount="70";
        int amount=Math.round(Float.parseFloat(sAmount)*100);


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+listView1.getText().toString()));
                startActivity(callIntent);

            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AvailableBoats.this, userboatview.class);
                finish();
            }
        });




        Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(noofriders.getText().toString().length()==0){
                    noofriders.requestFocus();
                    noofriders.setError("Please fill it properly");

                }
                else if(Integer.parseInt(noofriders.getText().toString())>15){
                    noofriders.requestFocus();
                    noofriders.setError("Number of passengers exceeds!! Fill less than 15");
                }

                else{
                    Checkout checkout=new Checkout();
                    checkout.setKeyID("rzp_test_lNoe3IbHONlY2Q");
                    checkout.setImage(R.drawable.logofirst);
                    JSONObject object=new JSONObject();
                    try {
                        object.put("name","MyNaav");
                        object.put("description","Payment for MyNaav");
                        object.put("theme.color","#F17800");
                        object.put("currency","INR");
                        object.put("amount",amount*(Integer.parseInt(noofriders.getText().toString())));
                        object.put("prefill-contact",phoneuser);
                        checkout.open(AvailableBoats.this,object);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });


    }
    @Override
    public void onPaymentSuccess(String s) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Payment ID");
        builder.setMessage(s);
        demotextview.setText(s);
        builder.show();
        sendData();



    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(AvailableBoats.this, "Payment Failed", Toast.LENGTH_SHORT).show();

    }

    public void openDialog(){

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
                    Toast.makeText(AvailableBoats.this, "Boat booked successfully", Toast.LENGTH_LONG).show();


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
                params.put("extra",currentDateandTime);
                params.put("paymentid",demotextview.getText().toString());
                params.put("onlydate",currentDate);
                params.put("onlytime",demotextview2.getText().toString());
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue1.add(request);
    }


}