package com.example.mynaav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class BankDetailsOfBoatOwner extends AppCompatActivity {
    String FullName, Age, EmailId, LicenseNo, Address1, Address2, Pincode, BoatSize, BoatStand, Validate;
    EditText AccountNo, BankName, IFSCCode,AccountHolderName,UPIid;
    Button button;
    TextView tandcboat;
    private String sendurl="https://mynaavproject.000webhostapp.com/boatownerdetailspush.php";
    private RequestQueue requestQueue;
    private static final String TAG=UserData.class.getSimpleName();
    int success;
    private String TAG_SUCCESS="success";
    private String TAG_MESSAGE="message";
    private String tag_json_obj="json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();

        setContentView(R.layout.activity_bank_details_of_boat_owner);
        FullName=getIntent().getStringExtra("FullName");
        Age=getIntent().getStringExtra("Age");
        BoatSize=getIntent().getStringExtra("BoatSize");
        EmailId=getIntent().getStringExtra("EmailId");
        LicenseNo=getIntent().getStringExtra("LicenseNo");
        Address1= getIntent().getStringExtra("Address1");
        Address2= getIntent().getStringExtra("Address2");
        Pincode=getIntent().getStringExtra("Pincode");
        BoatStand= getIntent().getStringExtra("BoatStand");
        AccountNo=findViewById(R.id.AccountNo);
        BankName=findViewById(R.id.BankName);
        IFSCCode=findViewById(R.id.IFSCCode);
        Validate="NO";
        tandcboat=findViewById(R.id.TnCboat);
        AccountHolderName=findViewById(R.id.AccountHolderName);
        UPIid=findViewById(R.id.UPIid);
        button=findViewById(R.id.proceed1);
        requestQueue= Volley.newRequestQueue(getApplicationContext());


        tandcboat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Boat_Privacy_Policy.class);
                startActivity(intent);
            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(UserData.this, "Enter your phone no correctly!!!", Toast.LENGTH_SHORT).show();

                    sendData();

            }
        });

    }

    private void sendData(){
        StringRequest request=new StringRequest(Request.Method.POST, sendurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    success = jobj.getInt(TAG_SUCCESS);
                    if (success == 1)
                        Toast.makeText(BankDetailsOfBoatOwner.this, jobj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(BankDetailsOfBoatOwner.this, jobj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(BankDetailsOfBoatOwner.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(),DetailsFetchedofBoatOwner.class);
                    startActivity(intent);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BankDetailsOfBoatOwner.this,error.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }

        }){
            public Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String,String>();
                params.put("FullName", FullName);
                params.put("Age", Age);
                params.put("BoatSize", BoatSize);
                params.put("EmailId", EmailId);
                params.put("LicenseNo", LicenseNo);
                params.put("Address1", Address1);
                params.put("Address2", Address2);
                params.put("Pincode", Pincode);
                params.put("BoatStand", BoatStand);

                params.put("AccountNo", AccountNo.getText().toString());
                params.put("IFSCCode", IFSCCode.getText().toString());
                params.put("AccountHolderName", AccountHolderName.getText().toString());
                params.put("UPIid", UPIid.getText().toString());
                params.put("Validate", Validate);
                params.put("Available", "YES");
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(request);
    }
}