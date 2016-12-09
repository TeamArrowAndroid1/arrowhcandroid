package com.arrow.arrowhc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class SignIn extends AppCompatActivity {
EditText user,paswd;Button sign_in;RequestQueue requestQueue;
    String url="https://arrowhc.herokuapp.com/profile/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__in);

        requestQueue= Volley.newRequestQueue(getBaseContext());
        user=(EditText)findViewById(R.id.usern);
        paswd=(EditText)findViewById(R.id.pswd);
        sign_in=(Button)findViewById(R.id.button);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  rurl="https://arrowhc.herokuapp.com/profile/"+user.getText().toString();
                // Toast.makeText(SignIn.this, rurl, Toast.LENGTH_SHORT).show();
                JsonArrayRequest req = new JsonArrayRequest(rurl,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    for (int i = 0; i <response.length(); i++) {
                                        JSONObject jresponse = response.getJSONObject(i);
                                        String name = jresponse.getString("name");
                                        String id = jresponse.getString("_id");
                                        String usern= jresponse.getString("username");
                                        String pswd = jresponse.getString("password");
                                        String dept = jresponse.getString("department");
                                        String profile=jresponse.getString("profile");
                                      //  Toast.makeText(SignIn.this, "hlooo", Toast.LENGTH_SHORT).show();
                                      //  Toast.makeText(SignIn.this, name+","+usern+","+pswd, Toast.LENGTH_SHORT).show();
                                        if(user.getText().toString().equals(usern) && paswd.getText().toString().equals(pswd))
                                        {
                                          //  Toast.makeText(SignIn.this, "valid", Toast.LENGTH_SHORT).show();
                                             //user.setText("");passwd.setText("");
                                            if(profile.equalsIgnoreCase("doctor") || profile.equalsIgnoreCase("nurse") ) {
                                                Intent intent = new Intent(getBaseContext(), StaffActivity.class);
                                                intent.putExtra("name", name);
                                                intent.putExtra("_id", id);
                                                startActivity(intent);
                                                overridePendingTransition(R.anim.activity_open_transition,R.anim.activity_closescale);
                                            }
                                            else if(profile.equalsIgnoreCase("patient"))
                                            {
                                                Intent intent = new Intent(getBaseContext(), PatientView.class);
                                                intent.putExtra("name", name);
                                                intent.putExtra("_id", id);
                                                startActivity(intent);
                                                overridePendingTransition(R.anim.activity_open_transition,R.anim.activity_closescale);
                                            }
                                        }
                                        else
                                        {
                                            Toast.makeText(SignIn.this, "usrname or pswd incorrect!", Toast.LENGTH_SHORT).show();
                                        }
                                        //  Toast.makeText(MainActivity.this, rurl, Toast.LENGTH_LONG).show();
                                        // Log.d("nickname",nickname);
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
        });

    }
}
