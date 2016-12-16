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

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddTest extends AppCompatActivity {
    EditText etCholesterol,etTemperature,etBlood,etHeart,tvResponse; TextView tvName;
    String sCholesterol,sTemperature,sBlood,sHeart,currentDateTimeString,sPatientName,sPatienId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);
        etCholesterol = (EditText) findViewById(R.id.etCholesterol);
        etTemperature = (EditText) findViewById(R.id.etTemperature);
        etBlood = (EditText) findViewById(R.id.etBlood);
        etHeart = (EditText) findViewById(R.id.etHeart);

        ActionBar ab = getSupportActionBar();
        ab.setDefaultDisplayHomeAsUpEnabled(true);

        tvName =(TextView)findViewById(R.id.tvName);
      //  tvResponse = (TextView) findViewById(R.id.textView2);

        sPatientName = getIntent().getStringExtra("name");
        sPatienId = getIntent().getStringExtra("id");
        if(sPatientName!=null)
        {
            tvName.setText(sPatientName);
        }

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(etCholesterol.getText().length()>2 && etTemperature.getText().length()>2 && etBlood.getText().length()>2 && etHeart.getText().length()>2) {
                     sCholesterol = etCholesterol.getText().toString();
                     sTemperature = etTemperature.getText().toString();
                     sBlood = etBlood.getText().toString();
                     sHeart = etHeart.getText().toString();

                     currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                          data();
                 }
                else
                 {
                     Toast.makeText(AddTest.this, "Please full info.!", Toast.LENGTH_SHORT).show();
                 }
                //Toast.makeText(this,"Put"+ paswdP+","+finalDid+","+ doctorName, Toast.LENGTH_LONG).show();


            }
    });

}

    public void data()
    {

        RequestQueue mRequestQueue = Volley.newRequestQueue(getBaseContext());

        String url = "https://arrowhc.herokuapp.com/test";

       // tvResponse.setText(url);

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            //    tvResponse.setText(response);
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("patient_name",sPatientName); //Add the data you'd like to send to the server.
                MyData.put("patient_id", sPatienId);
                MyData.put("blood_presure", sBlood);
                MyData.put("cholesterol", sCholesterol);
                MyData.put("heart_rate", sHeart);
                MyData.put("temperature", sTemperature);
                MyData.put("date", currentDateTimeString);

                return MyData;
            }
        };

        mRequestQueue.add(MyStringRequest);

        Intent intent=new Intent(getBaseContext(),PatientPage.class);
        intent.putExtra("id",sPatienId);
        intent.putExtra("name",sPatientName);
        startActivity(intent);

    }



}
