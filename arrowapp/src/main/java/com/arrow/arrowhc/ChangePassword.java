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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    String id, name, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ActionBar ab = getSupportActionBar();
        ab.setDefaultDisplayHomeAsUpEnabled(true);

        id = getIntent().getStringExtra("id");   //Doctor id to search for patients
        profile = getIntent().getStringExtra("profile");   //Doctor id to search for patients
        name = getIntent().getStringExtra("name");

        TextView tvName = (TextView) findViewById(R.id.textView4);
        tvName.setText(name);

        Button saveButton = (Button) findViewById(R.id.buttonPassword);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etNewPasswd = (EditText) findViewById(R.id.etNewPassword);
                EditText etRePasswd = (EditText) findViewById(R.id.etRePasswd);

                final String sNewPasswd = etNewPasswd.getText().toString();
                final String sRePasswd = etRePasswd.getText().toString();

                if (sNewPasswd.equals(sRePasswd)) {

                    RequestQueue mRequestQueue = Volley.newRequestQueue(getBaseContext());

                    String url = "https://arrowhc.herokuapp.com/profile/" + id;

                    StringRequest MyStringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                        }
                    }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //This code is executed if there is an error.
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> MyData = new HashMap<String, String>();
                            MyData.put("name", name); //Add the data you'd like to send to the server.
                            MyData.put("password", sNewPasswd);

                            return MyData;
                        }
                    };

                    mRequestQueue.add(MyStringRequest);

                    Toast.makeText(ChangePassword.this, "Your password has been changed", Toast.LENGTH_SHORT).show();
                    if (profile.equalsIgnoreCase("doctor") || profile.equalsIgnoreCase("nurse")) {
                        Intent intent = new Intent(getBaseContext(), StaffActivity.class);
                        intent.putExtra("_id", id);
                        intent.putExtra("profile", profile);
                        intent.putExtra("name", name);
                        startActivity(intent);

                    } else if (profile.equalsIgnoreCase("patient")) {
                        Intent intent = new Intent(getBaseContext(), PatientView.class);
                        intent.putExtra("_id", id);
                        intent.putExtra("profile", profile);
                        intent.putExtra("name", name);
                        startActivity(intent);
                    } else if (profile.equalsIgnoreCase("admin")) {
                        Intent intent = new Intent(getBaseContext(), AdminPage.class);
                        intent.putExtra("_id", id);
                        intent.putExtra("profile", profile);
                        intent.putExtra("name", name);
                        startActivity(intent);
                    } else {

                        Toast.makeText(ChangePassword.this, "The Re-Type Password is different than New Password", Toast.LENGTH_SHORT).show();

                    }

                }
            }


        });
    }
}
