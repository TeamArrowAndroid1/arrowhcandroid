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
    TextView name,id;
    FloatingActionButton f1,f2;
    ListView lv;
    String _id;
    ArrayList<HashMap<String,String>> arrayList;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view);

        requestQueue= Volley.newRequestQueue(getBaseContext());
        lv=(ListView)findViewById(R.id.new_List);
        arrayList=new ArrayList<>();


        name=(TextView)findViewById(R.id.pname);
        id=(TextView)findViewById(R.id.idd);
        _id=getIntent().getStringExtra("_id");
        String namee=getIntent().getStringExtra("name");

        Toast.makeText(this,"i is=" +_id, Toast.LENGTH_SHORT).show();
        if(namee!=null)
        {
            name.setText(namee);
            id.setText(_id);
        }

        f1=(FloatingActionButton)findViewById(R.id.callDoc);
        f2=(FloatingActionButton)findViewById(R.id.callNurse);

        data();
    }

    public void data()
    {
        String rurl="https://arrowhc.herokuapp.com/test/"+_id;

            //Toast.makeText(StaffActivity.this,rurl, Toast.LENGTH_SHORT).show();
            JsonArrayRequest req = new JsonArrayRequest(rurl,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            String da = "";

                       // Toast.makeText(PatientView.this, rurl, Toast.LENGTH_SHORT).show();
                            try {
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject jresponse = response.getJSONObject(i);

                                    String name = jresponse.getString("patient_name");
                                    String bp = jresponse.getString("blood_presure");
                                    String chole = jresponse.getString("cholesterol");
                                    String heart = jresponse.getString("heart_rate");
                                    String tempi = jresponse.getString("temperature");
                                    String date = jresponse.getString("date");


                                    //da=da+name+","+bp;
                                    //  String dept = jresponse.getString("department");
                                    HashMap<String, String> contact = new HashMap<>();

                                    contact.put("namee", name);
                                    contact.put("bp", bp);
                                    contact.put("chol", chole);
                                    contact.put("hb", heart);
                                    contact.put("temp", tempi);
                                    contact.put("date", date);
                                      //da=contact.get("namee");
                                    //Toast.makeText(PatientView.this,da, Toast.LENGTH_SHORT).show();

                                    //Toast.makeText(SignIn.this, name+","+usern+","+pswd, Toast.LENGTH_SHORT).show();

                                    // Toast.makeText(Staff_Data.this, id, Toast.LENGTH_SHORT).show();
                                    //  Toast.makeText(MainActivity.this, rurl, Toast.LENGTH_LONG).show();
                                    // Log.d("nickname",nickname);
                                    arrayList.add(contact);
                                    ListAdapter adapter = new SimpleAdapter(
                                            PatientView.this, arrayList,
                                            R.layout.s_n_list, new String[]{"namee", "bp", "chol",
                                            "hb", "temp","date"}, new int[]{R.id.datee,
                                            R.id.bP, R.id.chol, R.id.heartrate, R.id.temperatue, R.id.date});

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
     /*
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_transition,R.anim.activity_closescale);
    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
    }
    */
}
