// Team Arrow:
// Harpreet Singh
// Luis Esquivel
// Sagar Ranipa

package com.arrow.arrowhc;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateStaff extends AppCompatActivity {
     EditText Sname,Sdeptartment,Semail,Sphone,Susername,Spswd;
     String profile;
     Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_staff);
        ActionBar ab = getSupportActionBar();
        ab.setDefaultDisplayHomeAsUpEnabled(true);
        //declaration of textfiellds

        profile=getIntent().getStringExtra("profile");
        Toast.makeText(this, profile, Toast.LENGTH_SHORT).show();
        Sname=(EditText)findViewById(R.id.staffName);
        Sdeptartment=(EditText)findViewById(R.id.staffDept);
        Semail=(EditText)findViewById(R.id.staffEmail);
        Sphone=(EditText)findViewById(R.id.staffPhone);
        Susername=(EditText)findViewById(R.id.staffUserN);
        Spswd=(EditText)findViewById(R.id.staffPswd);

        //declaration of button

        create=(Button)findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(Sname.getText().length()>2 && Sdeptartment.getText().length()>2 && Semail.getText().length()>2 && Sphone.getText().length()>2 && Susername.getText().length()>2 && Spswd.getText().length()>2) {
                 addStaff();
             }
                else
             {
                 Toast.makeText(CreateStaff.this, "Please give Full info.!", Toast.LENGTH_SHORT).show();
             }
            }
        });

    }

    public void addStaff()
    {

        RequestQueue mRequestQueue = Volley.newRequestQueue(getBaseContext());
        String url = "https://arrowhc.herokuapp.com/profile";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                Intent intent=new Intent(getBaseContext(),StaffManagement.class);
                intent.putExtra("profile",profile);
                startActivity(intent);
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            //Add the data you'd like to send to the server.

            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("name",Sname.getText().toString());
                MyData.put("department", Sdeptartment.getText().toString());
                MyData.put("email", Semail.getText().toString());
                MyData.put("phone", Sphone.getText().toString());
                MyData.put("username", Susername.getText().toString());
                MyData.put("password", Spswd.getText().toString());
                MyData.put("profile",profile);


                return MyData;
            }
        };

        mRequestQueue.add(MyStringRequest);

    }
}
