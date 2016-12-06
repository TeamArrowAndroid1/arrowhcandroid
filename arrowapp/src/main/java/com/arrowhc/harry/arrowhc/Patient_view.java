package com.arrowhc.harry.arrowhc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Patient_view extends AppCompatActivity {
TextView name,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view);

        name=(TextView)findViewById(R.id.pname);
        id=(TextView)findViewById(R.id.idd);
        String namee=getIntent().getStringExtra("name");
        String idd=getIntent().getStringExtra("_id");
        if(namee!=null)
        {
            name.setText(namee);
            id.setText(idd);
        }
    }
}
