// Team Arrow:
// Harpreet Singh
// Luis Esquivel
// Sagar Ranipa

package com.arrow.arrowhc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

public class StaffManagement extends AppCompatActivity {

    ListView lv;
    Button addBtn;
    String profile,d_id;

    RequestQueue requestQueue;ArrayList<HashMap<String,String>> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_management);

           //initialize requestqueue and arraylist
              requestQueue= Volley.newRequestQueue(getBaseContext());
              arrayList=new ArrayList<>();

        profile=getIntent().getStringExtra("profile");
        Toast.makeText(this, profile, Toast.LENGTH_SHORT).show();
        //declare listview
        lv=(ListView)findViewById(R.id.staffLists);
        //declare addNew button
        addBtn=(Button)findViewById(R.id.addStaff);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getBaseContext(),CreateStaff.class);
                intent.putExtra("profile",profile);
                startActivity(intent);

            }
        });
             data();

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub
                d_id=arrayList.get(pos).get("id");

              //  Toast.makeText(StaffManagement.this, "pressed id is"+d_id, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder=new AlertDialog.Builder(StaffManagement.this);

                builder.setTitle("DELETE OPERATION!");
                builder.setMessage("Do you really want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String rurl = "https://arrowhc.herokuapp.com/profile/"+d_id;
                       deleteData(rurl);

                    }
                });

                builder.setNegativeButton("No",null);
                AlertDialog dialog=builder.create();
                dialog.show();
                return true;
            }
        });
    }

    public void data()
    {
        String  rurl="https://arrowhc.herokuapp.com/profile";

        JsonArrayRequest req = new JsonArrayRequest(rurl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i <response.length(); i++) {

                                JSONObject jresponse = response.getJSONObject(i);
                                String profilee = jresponse.getString("profile");
                                if(profilee.equalsIgnoreCase(profile)) {
                                    String name = jresponse.getString("name");
                                    String phone = jresponse.getString("phone");
                                    String email = jresponse.getString("email");
                                    String department = jresponse.getString("department");
                                    String username = jresponse.getString("username");
                                    String upswd = jresponse.getString("password");
                                    String iid = jresponse.getString("_id");
                                  //  Toast.makeText(StaffManagement.this, "incide after profile ", Toast.LENGTH_SHORT).show();
                                    HashMap<String, String> contact = new HashMap<>();

                                    contact.put("name", name);
                                    contact.put("phone", phone);
                                    contact.put("email", email);
                                    contact.put("department", department);
                                    contact.put("username", username);
                                    contact.put("password", upswd);
                                    contact.put("id", iid);
                                    arrayList.add(contact);
                                    ListAdapter adapter = new SimpleAdapter(
                                            StaffManagement.this, arrayList, R.layout.staff_list, new String[]{"name", "phone",
                                            "email", "department", "username", "password"}, new int[]{R.id.staffNames, R.id.staffphones,
                                            R.id.staffemails, R.id.staffDepts, R.id.staffusernames, R.id.staffpswds});

                                    lv.setAdapter(adapter);
                                }

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

    public void deleteData(String url)
    {

        RequestQueue mRequestQueue = Volley.newRequestQueue(getBaseContext());

        StringRequest MyStringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(StaffManagement.this, "Done!!", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getBaseContext(),StaffManagement.class);
                intent.putExtra("profile",profile);
                startActivity(intent);
               // tv.setText("Done");
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) ;


        mRequestQueue.add(MyStringRequest);

    }   //end newdata


}
