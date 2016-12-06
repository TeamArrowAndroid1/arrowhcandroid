package com.arrow.arrowhc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PatientPage extends AppCompatActivity {

    Button add,view,delete;View v; String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_page);
        id=getIntent().getStringExtra("id");
        //Toast.makeText(getBaseContext(), id, Toast.LENGTH_SHORT).show();
        add=(Button)findViewById(R.id.add);
        view=(Button)findViewById(R.id.viewp);
        delete=(Button)findViewById(R.id.deletep);

        //if there is no paitent id
           view.setVisibility(v.GONE);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),AddPatient.class);
                intent.putExtra("id",id);
               startActivity(intent);
            }
        });

    }
}
