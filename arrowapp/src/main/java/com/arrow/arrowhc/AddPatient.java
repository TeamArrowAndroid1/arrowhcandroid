// Team Arrow:
// Harpreet Singh
// Luis Esquivel
// Sagar Ranipa

package com.arrow.arrowhc;

import android.content.Intent;
import android.support.v7.app.ActionBar;
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

    String patientName,usernameP,paswdP,doctorName,finalDid,docUserName,NurseName,finalNid,NurseUserName,Pdepartment,PRoomNo;
    TextView tv;
    RequestQueue requestQueue;

    ArrayList<String> aarayListN;

    String profile="", name="", id="",nurseName;

    ArrayList<String> aarayListD;
    Spinner dsppiner,nsppiner;

    ArrayAdapter<String> adapterD;
    ArrayAdapter<String> adapterN;
    ArrayList<String> doctordata;
    ArrayAdapter<CharSequence> depts;
    ArrayAdapter<CharSequence> rooms;
    ArrayList<String> nursedata;

    Spinner departs,roomnos;

    Button saveP;


    EditText ename,euname,epswd;



    String NAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__patient);

        ActionBar ab = getSupportActionBar();
        ab.setDefaultDisplayHomeAsUpEnabled(true);


        id=getIntent().getStringExtra("id");   //Doctor id to search for patients
        profile=getIntent().getStringExtra("profile");   //Doctor id to search for patients
        name=getIntent().getStringExtra("name");



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


        data();  //to fetch data of all doctors and nurses to show in spinner



        depts=ArrayAdapter.createFromResource(getBaseContext(),R.array.departments,android.R.layout.simple_spinner_item);
        depts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departs.setAdapter(depts);

        rooms=ArrayAdapter.createFromResource(getBaseContext(),R.array.rooms,android.R.layout.simple_spinner_item);
        rooms.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomnos.setAdapter(rooms);

        dsppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             //get doctor by selecting from doctor spinner(dropdown)
                doctorName=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nsppiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //get Nurse by selecting from Nurse spinner(dropdown)
                nurseName=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

         departs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 //get department for patient by selecting from department spinner(dropdown)
                 Pdepartment=parent.getItemAtPosition(position).toString();

             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

           roomnos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                   //set room for patient by selecting from rooms spinner(dropdown)
                   PRoomNo=parent.getItemAtPosition(position).toString();

               }

               @Override
               public void onNothingSelected(AdapterView<?> parent) {

               }
           });
        saveP=(Button)findViewById(R.id.addpatientt);
        saveP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  //Text fields should not be empty
                if(ename.getText().length()>2 && euname.getText().length()>2 && epswd.length()>2) {
                    newdata();  //save patient data
                    if (id != null && profile != null) {

                        //go back to staff page and new patient will be there in list of patients
                        Intent intent = new Intent(getBaseContext(), StaffActivity.class);
                        intent.putExtra("_id", id);
                        intent.putExtra("profile", profile);
                        intent.putExtra("name", name);
                        startActivity(intent);

                    }
                }
                else
                {
                    Toast.makeText(AddPatient.this, "Please fill full info.!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



 public void data()
 {
   final   String  rurl="https://arrowhc.herokuapp.com/profile";


     JsonArrayRequest req = new JsonArrayRequest(rurl,
             new Response.Listener<JSONArray>() {
                 @Override
                 public void onResponse(JSONArray response) {
                     String da="";


                     try {
                         for (int i = 0; i <response.length(); i++) {
                             JSONObject jresponse = response.getJSONObject(i);
                             String proname = jresponse.getString("profile");

                              if(proname.equalsIgnoreCase("nurse"))
                              {

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


                              }
                             else if(proname.equalsIgnoreCase("doctor"))
                              {

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


                              }

                         }



                         adapterD=new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,aarayListD);
                         adapterD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                         dsppiner.setAdapter(adapterD);

                         adapterN=new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,aarayListN);
                         adapterN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                         nsppiner.setAdapter(adapterN);





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
            //get doctorname , nurseName,Department,room

        int totalElements = doctordata.size();


        //loop through it
        for(int index=0; index < totalElements; index++)
        {

               String dd=doctordata.get(index).toString();

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

            }



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

            }
        }
        Toast.makeText(this,"Put"+ paswdP+","+finalDid+","+ doctorName, Toast.LENGTH_LONG).show();
            RequestQueue mRequestQueue = Volley.newRequestQueue(getBaseContext());
        String url = "https://arrowhc.herokuapp.com/patient";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                tv.setText("Done");

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
         //code to perform activity change transition
        overridePendingTransition(R.anim.activity_open_transition,R.anim.activity_closescale);

    }
}
