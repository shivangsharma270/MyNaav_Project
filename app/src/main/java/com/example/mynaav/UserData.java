package com.example.mynaav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserData extends AppCompatActivity {

    EditText fullnameofuser,ageofuser,emailofuser,addressofuser,pincodeofuser;
    Button Senddata;
    String phoneofuser;
    Spinner genderofuser;
    private String sendurl="https://mynaavproject.000webhostapp.com/get_data.php";
    private RequestQueue requestQueue;
    private static final String TAG=UserData.class.getSimpleName();
    int success;
    private String TAG_SUCCESS="success";
    private String TAG_MESSAGE="message";
    private String tag_json_obj="json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_data);

        fullnameofuser=findViewById(R.id.fullname);
        ageofuser=findViewById(R.id.age);
        genderofuser=findViewById(R.id.gender);
        emailofuser=findViewById(R.id.emailid);
        phoneofuser = getIntent().getStringExtra("Phoneno");
        addressofuser=findViewById(R.id.addressline2);
        pincodeofuser=findViewById(R.id.pincode);
        Senddata=findViewById(R.id.VERIFY);
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        Senddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ageofuser.length() > 3) {
                    ageofuser.requestFocus();
                    ageofuser.setError("Invalid Age");
                }
                else if (!emailofuser.getText().toString().contains("@") || !emailofuser.getText().toString().contains(".com")) {
                    emailofuser.requestFocus();
                    emailofuser.setError("Invalid email id");

                }


                    //Toast.makeText(UserData.this, "Enter your phone no correctly!!!", Toast.LENGTH_SHORT).show();
                else if (pincodeofuser.length() != 6) {
                    pincodeofuser.requestFocus();
                    pincodeofuser.setError("Invalid Pincode");

                    //Toast.makeText(UserData.this, "Enter 6 digit pincode!!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendData();
                }


            }
        });




        Spinner myspinner3 = findViewById(R.id.gender);
        ArrayAdapter<String> adapter3= new ArrayAdapter<String>(UserData.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Gender));
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner3.setAdapter(adapter3);





    }

    private void sendData(){
        StringRequest request=new StringRequest(Request.Method.POST, sendurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    success = jobj.getInt(TAG_SUCCESS);
                    if (success == 1)
                        Toast.makeText(UserData.this, jobj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(UserData.this, jobj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(UserData.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(), userboatview.class);
                    startActivity(intent);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserData.this,error.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }

        }){
            public Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String,String>();
                params.put("fullname", fullnameofuser.getText().toString());
                params.put("age", ageofuser.getText().toString());
                params.put("gender", genderofuser.getSelectedItem().toString());
                params.put("emailid", emailofuser.getText().toString());
                params.put("phoneno", phoneofuser);
                params.put("address", addressofuser.getText().toString());
                params.put("pincode", pincodeofuser.getText().toString());
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(request);
    }
}