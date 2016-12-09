package com.arrow.arrowhc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class StaffActivity extends AppCompatActivity {
    TextView tv;ListView lv;ArrayList<HashMap<String,String>> arrayList;String id,name;
    RequestQueue requestQueue;
    ImageButton searchb;Button addb;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_);
        floatingActionButton1=(FloatingActionButton)findViewById(R.id.searching);
        floatingActionButton2=(FloatingActionButton)findViewById(R.id.adding);
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),SearchActivity.class);
                startActivity(intent);
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),AddPatient.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_open_transition,R.anim.activity_closescale);
            }
        });



        //initialize requestqueue && Arraylist
        requestQueue= Volley.newRequestQueue(getBaseContext());
        arrayList=new ArrayList<>();
        lv=(ListView)findViewById(R.id.list) ;
        tv=(TextView)findViewById(R.id.textView);
        tv.setText("hlo!");
         id=getIntent().getStringExtra("_id");
        name=getIntent().getStringExtra("name");
        if(id!=null)
        {
            tv.setText(name);
        }
        data();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                      String name=arrayList.get(position).get("namee");
                      String room=arrayList.get(position).get("roomm");
                String iiid=arrayList.get(position).get("id");
                    Intent intent=new Intent(getBaseContext(),PatientPage.class);
                        intent.putExtra("id",iiid);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_open_transition,R.anim.activity_closescale);
            }
        });



    }

//dont forget to call this method in oncreate()
//https://arrowhc.herokuapp.com/patient
    public void data()
    {
        String  rurl="https://arrowhc.herokuapp.com/doctorpatients/"+id;

        //Toast.makeText(StaffActivity.this,rurl, Toast.LENGTH_SHORT).show();
        JsonArrayRequest req = new JsonArrayRequest(rurl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String da="";

                      //  Toast.makeText(StaffActivity.this, url, Toast.LENGTH_SHORT).show();
                        try {
                            for (int i = 0; i <response.length(); i++) {
                                JSONObject jresponse = response.getJSONObject(i);
                                String name = jresponse.getString("patient_name");
                                String did = jresponse.getString("doc_name");
                                String nurse= jresponse.getString("nurse_name");
                                String room = jresponse.getString("room_no");
                                String deptt=jresponse.getString("department");
                                String pid=jresponse.getString("_id");
                                //  da=da+name+","+did;
                                //  String dept = jresponse.getString("department");
                                HashMap<String,String> contact = new HashMap<>();

                                contact.put("namee",name);
                                contact.put("didd",did);
                                contact.put("nursee",nurse);
                                contact.put("roomm",room);
                                contact.put("dept",deptt);
                                contact.put("id",pid);
                              //  da=contact.get("namee");
                                Toast.makeText(StaffActivity.this,da, Toast.LENGTH_SHORT).show();

                                //Toast.makeText(SignIn.this, name+","+usern+","+pswd, Toast.LENGTH_SHORT).show();

                                // Toast.makeText(Staff_Data.this, id, Toast.LENGTH_SHORT).show();
                                //  Toast.makeText(MainActivity.this, rurl, Toast.LENGTH_LONG).show();
                                // Log.d("nickname",nickname);
                                arrayList.add(contact);
                                ListAdapter adapter = new SimpleAdapter(
                                        StaffActivity.this, arrayList,
                                        R.layout.list_item, new String[]{"namee","nursee","didd",
                                        "roomm","dept"}, new int[]{R.id.patient,
                                        R.id.doctor, R.id.nrse,R.id.room,R.id.dept});

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

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_transition,R.anim.activity_closescale);
    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
    }
}
