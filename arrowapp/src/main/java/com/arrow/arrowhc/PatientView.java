package com.arrow.arrowhc;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PatientView extends AppCompatActivity {
    TextView tvname,tvid;
    FloatingActionButton f1,f2;
    ListView lv;
    String id, username;
    ArrayList<HashMap<String,String>> arrayList;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view);

        requestQueue= Volley.newRequestQueue(getBaseContext());
        arrayList=new ArrayList<>();
        lv=(ListView)findViewById(R.id.list);


        tvname=(TextView)findViewById(R.id.pname);
        tvid=(TextView)findViewById(R.id.idd);
        //id=getIntent().getStringExtra("id");
        username=getIntent().getStringExtra("username");
        String namee=getIntent().getStringExtra("name");

        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        if(namee!=null)
        {
            tvname.setText(namee);
            //tvid.setText(id);
        }

        f1=(FloatingActionButton)findViewById(R.id.callDoc);
        f2=(FloatingActionButton)findViewById(R.id.callNurse);

        id = tvid.getText().toString();
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        data();

    }

    public void data()
    {
        String  rurl="https://arrowhc.herokuapp.com/patient/"+username;

        Toast.makeText(this, PatientView.this.tvid.getText().toString(), Toast.LENGTH_SHORT).show();

        JsonArrayRequest req = new JsonArrayRequest(rurl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i <response.length(); i++) {

                                JSONObject jresponse = response.getJSONObject(i);

                                String blood = jresponse.getString("blood_presure");
                                String cholesterol= jresponse.getString("cholesterol");
                                String heart = jresponse.getString("heart_rate");
                                String temperature = jresponse.getString("temperature");
                                String testdate = jresponse.getString("date");

                                HashMap<String,String> contact = new HashMap<>();

                                contact.put("date",testdate);
                                contact.put("blood",blood);
                                contact.put("cholesterol",cholesterol);
                                contact.put("heart",heart);
                                contact.put("temperature",temperature);

                                arrayList.add(contact);
                                ListAdapter adapter = new SimpleAdapter(
                                        PatientView.this, arrayList, R.layout.list_test, new String[]{"date","blood",
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
