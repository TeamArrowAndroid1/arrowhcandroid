package com.arrow.arrowhc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PatientPage extends AppCompatActivity {

    TextView tv;ListView lv;
    ArrayList<HashMap<String,String>> arrayList;
    RequestQueue requestQueue;

    Button add,view,delete,test;View v;
    String id, patientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_page);

        requestQueue= Volley.newRequestQueue(getBaseContext());
        arrayList=new ArrayList<>();
        lv=(ListView)findViewById(R.id.list);

        id=getIntent().getStringExtra("id");
        patientName=getIntent().getStringExtra("name");

        //Toast.makeText(getBaseContext(), id, Toast.LENGTH_SHORT).show();
        tv = (TextView) findViewById(R.id.textView);
        tv.setText(id);

        add=(Button)findViewById(R.id.add);
        view=(Button)findViewById(R.id.viewp);
        delete=(Button)findViewById(R.id.deletep);

        test=(Button)findViewById(R.id.addTest);

        //if there is no paitent id
           view.setVisibility(v.GONE);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),AddPatient.class);
                intent.putExtra("id",id);
               startActivity(intent);
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),AddTest.class);
                intent.putExtra("id",id);
                intent.putExtra("name",patientName);
                startActivity(intent);
            }
        });

        data();

    }

    public void data()
    {
        String  rurl="https://arrowhc.herokuapp.com/test/"+id;

        JsonArrayRequest req = new JsonArrayRequest(rurl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //String da="";

                        //  Toast.makeText(StaffActivity.this, url, Toast.LENGTH_SHORT).show();
                        try {
                            for (int i = 0; i <response.length(); i++) {

                                JSONObject jresponse = response.getJSONObject(i);

                                String blood = jresponse.getString("blood_presure");
                                String cholesterol= jresponse.getString("cholesterol");
                                String heart = jresponse.getString("heart_rate");
                                String temperature = jresponse.getString("temperature");
                                String testdate = jresponse.getString("date");
                                Toast.makeText(PatientPage.this,testdate, Toast.LENGTH_SHORT).show();
                                HashMap<String,String> contact = new HashMap<>();

                                contact.put("date",testdate);
                                contact.put("blood",blood);
                                contact.put("cholesterol",cholesterol);
                                contact.put("heart",heart);
                                contact.put("temperature",temperature);

                                arrayList.add(contact);
                                ListAdapter adapter = new SimpleAdapter(
                                        PatientPage.this, arrayList, R.layout.list_test, new String[]{"date","blood",
                                        "cholesterol","heart","temperature"}, new int[]{R.id.date,R.id.blood,
                                        R.id.cholesterol,R.id.heart,R.id.temperature});

                                lv.setAdapter(adapter);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");
            }
        }
        );
        requestQueue.add(req);


    }

}
