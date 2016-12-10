package com.arrow.arrowhc;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
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

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);

        ActionBar ab = getSupportActionBar();
        ab.setDefaultDisplayHomeAsUpEnabled(true);

        TextView tvName =(TextView)findViewById(R.id.tvName);
        final TextView tvResponse = (TextView) findViewById(R.id.textView2);

        final String sPatientName = getIntent().getStringExtra("name");
        final String sPatienId = getIntent().getStringExtra("id");
        if(sPatientName!=null)
        {
            tvName.setText(sPatientName);
        }

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etCholesterol = (EditText) findViewById(R.id.etCholesterol);
                EditText etTemperature = (EditText) findViewById(R.id.etTemperature);
                EditText etBlood = (EditText) findViewById(R.id.etBlood);
                EditText etHeart = (EditText) findViewById(R.id.etHeart);

                final String sCholesterol = etCholesterol.getText().toString();
                final String sTemperature = etTemperature.getText().toString();
                final String sBlood = etBlood.getText().toString();
                final String sHeart = etHeart.getText().toString();

                final String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                //Toast.makeText(this,"Put"+ paswdP+","+finalDid+","+ doctorName, Toast.LENGTH_LONG).show();

                RequestQueue mRequestQueue = Volley.newRequestQueue(getBaseContext());

                String url = "https://arrowhc.herokuapp.com/test";

                tvResponse.setText(url);

                StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        tvResponse.setText(response);
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
                startActivity(intent);

            }
        });

    }

}
