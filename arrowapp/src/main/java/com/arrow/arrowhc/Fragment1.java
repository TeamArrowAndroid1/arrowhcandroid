// Team Arrow:
// Harpreet Singh
// Luis Esquivel
// Sagar Ranipa

package com.arrow.arrowhc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Team-Arrow on 2016-12-01.
 * Harpreet Singh
 */


public class Fragment1 extends Fragment {
     EditText e;Button btn;ListView list;
     RequestQueue requestQueue;ArrayList<HashMap<String,String>> arayList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        v=inflater.inflate(R.layout.frag1,container,false);
        requestQueue= Volley.newRequestQueue(getContext());
        arayList=new ArrayList<>();
       e=(EditText)v.findViewById(R.id.by_name);
        list=(ListView)v.findViewById(R.id.frag_one_l);
        btn=(Button)v.findViewById(R.id.by_n);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!arayList.isEmpty()) {
                    arayList.clear();
                }
                if(e.getText()!=null)
                {
                    requestj();
                }

                else
                {

                    Toast.makeText(getContext(), "Please enter name!", Toast.LENGTH_SHORT).show();

                }
            }
        });






        return v;
    }

    public void requestj()
    {
        String  rurl="https://arrowhc.herokuapp.com/patient";


        JsonArrayRequest req = new JsonArrayRequest(rurl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i <response.length(); i++) {
                                JSONObject jresponse = response.getJSONObject(i);
                                String name = jresponse.getString("patient_name");
                                String did = jresponse.getString("doc_name");
                                String nurse= jresponse.getString("nurse_name");
                                String room = jresponse.getString("room_no");
                                String deptt=jresponse.getString("department");

                                if(e.getText().toString().equalsIgnoreCase(name)) {

                                    HashMap<String, String> storer = new HashMap<>();

                                    storer.put("namee", name);
                                    storer.put("didd", did);
                                    storer.put("nursee", nurse);
                                    storer.put("roomm", room);
                                    storer.put("dept", deptt);


                                    arayList.add(storer);

                                   ;

                                    ListAdapter adapter = new SimpleAdapter(
                                            getActivity(), arayList,
                                            R.layout.list_item, new String[]{"namee", "nursee", "didd",
                                            "roomm", "dept"}, new int[]{R.id.patient,
                                            R.id.doctor, R.id.nrse, R.id.room, R.id.dept});

                                    list.setAdapter(adapter);
                                }
                                else
                                {

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
