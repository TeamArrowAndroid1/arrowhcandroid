package com.arrow.arrowhc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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


/**
 * Created by Harry on 2016-12-01.
 */
public class Fragment2 extends Fragment {
EditText e2;ImageButton searchb;ArrayList<HashMap<String,String>> arrryList;RequestQueue requestQueue;
    ListView ls;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.frag2,container,false);
        requestQueue= Volley.newRequestQueue(getContext());
        arrryList=new ArrayList<>();
        e2=(EditText)v.findViewById(R.id.by_dept);
        searchb=(ImageButton)v.findViewById(R.id.by_d);
        ls=(ListView)v.findViewById(R.id.frag_two_l);

        searchb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!arrryList.isEmpty())
                {
                    arrryList.clear();
                }
                if(e2.getText()!=null)
                {
                    search_d();
                }
                else if(e2.getText()==null)
                {
                    Toast.makeText(getContext(), "Plese enter department first!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    public void search_d()
    {
        String  rurl="https://arrowhc.herokuapp.com/patient";

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

                                if(e2.getText().toString().equals(deptt)) {
                                    Toast.makeText(getContext(), "matched", Toast.LENGTH_SHORT).show();

                                    //  da=da+name+","+did;
                                    //  String dept = jresponse.getString("department");
                                    HashMap<String, String> storer = new HashMap<>();

                                    storer.put("namee", name);
                                    storer.put("didd", did);
                                    storer.put("nursee", nurse);
                                    storer.put("roomm", room);
                                    storer.put("dept", deptt);
                                    da = storer.get("dept");
                                    Toast.makeText(getContext(), da, Toast.LENGTH_SHORT).show();

                                    //Toast.makeText(SignIn.this, name+","+usern+","+pswd, Toast.LENGTH_SHORT).show();

                                    // Toast.makeText(Staff_Data.this, id, Toast.LENGTH_SHORT).show();
                                    //  Toast.makeText(MainActivity.this, rurl, Toast.LENGTH_LONG).show();
                                    // Log.d("nickname",nickname);
                                    arrryList.add(storer);

                                    // Toast.makeText(SearchActivity.this, (CharSequence) arayList, Toast.LENGTH_SHORT).show();

                                    ListAdapter adapter = new SimpleAdapter(
                                            getActivity(), arrryList,
                                            R.layout.list_item, new String[]{"namee", "nursee", "didd",
                                            "roomm", "dept"}, new int[]{R.id.patient,
                                            R.id.doctor, R.id.nurse, R.id.room, R.id.dept});

                                    ls.setAdapter(adapter);
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Sry No Data with this name!", Toast.LENGTH_SHORT).show();
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
}
