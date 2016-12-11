package com.arrow.arrowhc;

import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AddPatient extends AppCompatActivity {
TextView tv;RequestQueue requestQueue;ArrayList<String> aarayListN;
    String nurses[],doctors[];String daa;String  rurl;int p=0,q=0; String id=""; String profile="";String name="";String doc_name="";
    ArrayList<String> aarayListD;Spinner dsppiner,nsppiner;ArrayAdapter<String> adapterD;ArrayAdapter<String> adapterN;
    ArrayList<String> doctordata;ArrayAdapter<CharSequence> depts;ArrayAdapter<CharSequence> rooms;
    ArrayList<String> nursedata;Spinner departs,roomnos;Button saveP;
    String patientName,usernameP,paswdP,doctorName,finalDid,docUserName,NurseName,finalNid,NurseUserName,Pdepartment,PRoomNo;

    EditText ename,euname,epswd;String docName,docId,dUserName,nurseName,nurseId,nurseUserName,departMent,roomNo;
    String NAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__patient);

        id=getIntent().getStringExtra("id");   //Doctor id to search for patients
        profile=getIntent().getStringExtra("profile");   //Doctor id to search for patients
        name=getIntent().getStringExtra("name");


        Toast.makeText(this, name + " " + profile, Toast.LENGTH_SHORT).show();
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
                doctorName=parent.getItemAtPosition(position).toString();
               // Toast.makeText(AddPatient.this, docName, Toast.LENGTH_SHORT).show();
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
              //  Toast.makeText(AddPatient.this, nurseName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

         departs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 Pdepartment=parent.getItemAtPosition(position).toString();
               //  Toast.makeText(AddPatient.this,departMent, Toast.LENGTH_SHORT).show();
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

           roomnos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   PRoomNo=parent.getItemAtPosition(position).toString();
                 //  Toast.makeText(AddPatient.this,roomNo, Toast.LENGTH_SHORT).show();
               }

               @Override
               public void onNothingSelected(AdapterView<?> parent) {

               }
           });
        saveP=(Button)findViewById(R.id.addpatientt);
        saveP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newdata();  //save patient data
                if(id!=null && profile!=null) {
                    Intent intent = new Intent(getBaseContext(), StaffActivity.class);
                    intent.putExtra("_id", id);
                    intent.putExtra("profile", profile);
                    intent.putExtra("name", name);
                    startActivity(intent);

                }
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
                                  String nData=contact.get("n_data");
                                  aarayListN.add(n+"(#"+un+")");
                                  nursedata.add(nData);
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

                       // Toast.makeText(AddPatient.this, "docotr data list"+doctordata.size(), Toast.LENGTH_SHORT).show();


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
                 patientName=ename.getText().toString();
           usernameP=euname.getText().toString();
          paswdP=epswd.getText().toString();
            //get docname , nurseName,Department,room
           String finalid="";
           String finalNurseId="";
        int totalElements = doctordata.size();


        //loop through it
        for(int index=0; index < totalElements; index++)
        {

               String dd=doctordata.get(index).toString();
           // Toast.makeText(getBaseContext(), dd, Toast.LENGTH_LONG).show();
            String parts[] = dd.split(",");

             String iid=parts[0];
            String iname=parts[1];
            String iuname=parts[2];


            String nameUsername[]=doctorName.split(Pattern.quote("("));
             String rawUser=nameUsername[1];
              String dUName[]=rawUser.split(Pattern.quote("#"));
            String medioum=dUName[1];
              String rawUSER[]=medioum.split(Pattern.quote(")"));

             NAME=nameUsername[0];
             docUserName=rawUSER[0];
            if(NAME.equalsIgnoreCase(iname) && docUserName.equalsIgnoreCase(iuname))
            {
                finalDid=iid;
               // Toast.makeText(this,"doctor id= " +finalDid, Toast.LENGTH_SHORT).show();
            }
          //  Toast.makeText(this, nameUsername[0]+","+finalUserName, Toast.LENGTH_SHORT).show();


        }

        int nurseelements=nursedata.size();
        for(int j=0;j<nurseelements;j++)
        {
            String nn=nursedata.get(j).toString();
            String partsN[]=nn.split(",");
            String niid=partsN[0];
            String nname=partsN[1];
            String nuname=partsN[2];

            String nurseUserName[]=nurseName.split(Pattern.quote("("));
            String rawNUser=nurseUserName[1];
            String nUName[]=rawNUser.split(Pattern.quote("#"));
            String medium=nUName[1];
            String rawNUSER[]=medium.split(Pattern.quote(")"));

             NurseName=nurseUserName[0];
             NurseUserName=rawNUSER[0];
            if(NurseName.equalsIgnoreCase(nname) && NurseUserName.equalsIgnoreCase(nuname))
            {
                finalNid=niid;
              //  Toast.makeText(this, "nurse id"+finalNid, Toast.LENGTH_SHORT).show();
            }
        }
        Toast.makeText(this,"Put"+ paswdP+","+finalDid+","+ doctorName, Toast.LENGTH_LONG).show();
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
                MyData.put("patient_name",patientName); //Add the data you'd like to send to the server.
                MyData.put("room_no", PRoomNo);
                MyData.put("username", usernameP);
                MyData.put("password", paswdP);
                MyData.put("doc_id", finalDid);
                MyData.put("doc_name", NAME);
                MyData.put("doc_username", docUserName);
                MyData.put("department",Pdepartment);
                MyData.put("nurse_id",finalNid);
                MyData.put("nurse_name", NurseName);
                MyData.put("nurse_username", NurseUserName);

                MyData.put("name", patientName);
                MyData.put("profile", "patient");

                return MyData;
            }
        };

        mRequestQueue.add(MyStringRequest);

    }   //end newdata


    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(R.anim.activity_open_transition,R.anim.activity_closescale);

    }
}
