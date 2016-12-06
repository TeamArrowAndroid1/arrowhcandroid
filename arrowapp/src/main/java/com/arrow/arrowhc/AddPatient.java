package com.arrow.arrowhc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class AddPatient extends AppCompatActivity {
TextView tv;RequestQueue requestQueue;ArrayList<String> aarayListN;
    String nurses[],doctors[];String daa;String  rurl;int p=0,q=0; String da=""; String proofile="";String nurse_name="";String doc_name="";
    ArrayList<String> aarayListD;Spinner dsppiner,nsppiner;ArrayAdapter<String> adapterD;ArrayAdapter<String> adapterN;
    ArrayList<String> doctordata;ArrayAdapter<CharSequence> depts;ArrayAdapter<CharSequence> rooms;
    ArrayList<HashMap<String,String>> nursedata;Spinner departs,roomnos;Button saveP;

    EditText ename,euname,epswd;String docName,docId,dUserName,nurseName,nurseId,nurseUserName,departMent,roomNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__patient);
        requestQueue= Volley.newRequestQueue(getBaseContext());
        doctordata=new ArrayList<>();
        nursedata=new ArrayList<>();
        aarayListN=new ArrayList<>();
        aarayListD=new ArrayList<>();
        tv=(TextView)findViewById(R.id.show);


        ename=(EditText)findViewById(R.id.pname);
        dsppiner=(Spinner)findViewById(R.id.dspinner);
        nsppiner=(Spinner)findViewById(R.id.nspinner);
        departs=(Spinner)findViewById(R.id.departs);
        roomnos=(Spinner)findViewById(R.id.rr);
        euname=(EditText)findViewById(R.id.p_usernm);
        epswd=(EditText)findViewById(R.id.psswdd);
        saveP=(Button)findViewById(R.id.addpatientt);


        data();



        depts=ArrayAdapter.createFromResource(getBaseContext(),R.array.departments,android.R.layout.simple_spinner_item);
        depts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departs.setAdapter(depts);
        rooms=ArrayAdapter.createFromResource(getBaseContext(),R.array.rooms,android.R.layout.simple_spinner_item);
        rooms.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomnos.setAdapter(rooms);

        dsppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              //  String docy= (String) parent.getItemAtPosition(position);
                docName=parent.getItemAtPosition(position).toString();
                Toast.makeText(AddPatient.this, docName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nsppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  String docy= (String) parent.getItemAtPosition(position);
                nurseName=parent.getItemAtPosition(position).toString();
                Toast.makeText(AddPatient.this, nurseName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

         departs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 departMent=parent.getItemAtPosition(position).toString();
                 Toast.makeText(AddPatient.this,departMent, Toast.LENGTH_SHORT).show();
             }
         });

           roomnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   roomNo=parent.getItemAtPosition(position).toString();
                   Toast.makeText(AddPatient.this,roomNo, Toast.LENGTH_SHORT).show();
               }
           });

        saveP=(Button)findViewById(R.id.addpatientt);
        saveP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newdata();  //save patient data
            }
        });
    }



 public void data()
 {
   final   String  rurl="https://arrowhc.herokuapp.com/profile";

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
                             String proname = jresponse.getString("profile");
                           //  Toast.makeText(AddPatient.this, proname, Toast.LENGTH_SHORT).show();
                              if(proname.equalsIgnoreCase("nurse"))
                              {
                                //  Toast.makeText(AddPatient.this,"enet into nurses", Toast.LENGTH_SHORT).show();
                                   String nurid=jresponse.getString("_id");
                                  String nurse_name=jresponse.getString("name");
                                  String nusername=jresponse.getString("username");
                                  HashMap<String,String> contact = new HashMap<>();
                                  contact.put("nid",nurid);
                                  contact.put("nname",nurse_name);
                                  contact.put("nuname",nusername);
                                  contact.put("n_data",nurid+","+nurse_name+","+nusername);
                                   String n=contact.get("nname");
                                   String un=contact.get("nuname");
                                  aarayListN.add(n+"(#"+un+")");
                                  nursedata.add(contact);
                                 // Toast.makeText(AddPatient.this,"data in heap"+ da, Toast.LENGTH_SHORT).show();

                              }
                             else if(proname.equalsIgnoreCase("doctor"))
                              {
                                //  Toast.makeText(AddPatient.this, "Enter into doctors", Toast.LENGTH_SHORT).show();
                                  String docid=jresponse.getString("_id");
                                  String doctor_name=jresponse.getString("name");
                                  String dusername=jresponse.getString("username");
                                  HashMap<String,String> contactd = new HashMap<>();
                                  contactd.put("did",docid);
                                 contactd.put("dname",doctor_name);
                                  contactd.put("duname",dusername);
                                contactd.put("d_data",docid+","+doctor_name+","+dusername);
                                  da=contactd.get("dname");
                                  String du=contactd.get("duname");
                                  String dii=contactd.get("did");
                                  aarayListD.add(da+"(#"+du+")");
                                  doctordata.add(dii+","+da+","+du);
                                 // Toast.makeText(AddPatient.this, "doctor heap"+da, Toast.LENGTH_SHORT).show();

                              }

                         }
                        // Toast.makeText(AddPatient.this, "nurse list"+aarayListN.size(), Toast.LENGTH_SHORT).show();

                          adapterD=new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,aarayListD);
                          adapterD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                         dsppiner.setAdapter(adapterD);
                         adapterN=new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,aarayListN);
                         adapterN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                         nsppiner.setAdapter(adapterN);

                        Toast.makeText(AddPatient.this, "docotr data list"+doctordata.size(), Toast.LENGTH_SHORT).show();


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

    public void newdata()
    {
          String patientName=ename.getText().toString();
            //get docname , nurseName,Department,room

        int totalElements = doctordata.size();

        System.out.println("ArrayList contains...");
        //loop through it
        for(int index=0; index < totalElements; index++)
        {
            // Toast.makeText(getBaseContext(), doctordata.get(index).toString(), Toast.LENGTH_LONG).show();
               String dd=doctordata.get(index).toString();
        }



                /*

        RequestQueue mRequestQueue = Volley.newRequestQueue(getBaseContext());
        String url = "https://arrowhc.herokuapp.com/patient";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                tv.setText("Done");
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
                MyData.put("patient_name", "Ronald"); //Add the data you'd like to send to the server.
                MyData.put("room_no", "112");
                MyData.put("username", "ron");
                MyData.put("password", "her");
                MyData.put("doc_id", "583dcc7e5a034e0519757672");
                MyData.put("doc_name", "Dr Luis");
                MyData.put("doc_username", "luis");
                MyData.put("department", "Neurology");
                MyData.put("nurse_id", "583dccc05a034e0519758643");
                MyData.put("nurse_name", "Nr Laura");
                MyData.put("nurse_username", "eli");

                return MyData;
            }
        };

        mRequestQueue.add(MyStringRequest);
             */
    }   //end newdata

}
