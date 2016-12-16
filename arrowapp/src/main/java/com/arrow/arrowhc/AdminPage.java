// Team Arrow:
// Harpreet Singh
// Luis Esquivel
// Sagar Ranipa

package com.arrow.arrowhc;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

public class AdminPage extends AppCompatActivity {
     FloatingActionButton adminBtn,doctorsBtn,nursesBtn;
    TextView tvname;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        name=getIntent().getStringExtra("name");

        //declaration of TextView
        tvname=(TextView)findViewById(R.id.adminName);
        if(name!=null)
        {
            tvname.setText(name);
        }
            //declaration of floating buttons
         adminBtn=(FloatingActionButton)findViewById(R.id.adminA);
        doctorsBtn=(FloatingActionButton)findViewById(R.id.docA);
        nursesBtn=(FloatingActionButton)findViewById(R.id.nurA);

         doctorsBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 // goto add new doctor
                 Intent intent=new Intent(getBaseContext(),StaffManagement.class);
                 intent.putExtra("profile","doctor");

                 startActivity(intent);

             }
         });

        nursesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // goto add new nurse
                Intent intent=new Intent(getBaseContext(),StaffManagement.class);
                intent.putExtra("profile","nurse");
                startActivity(intent);

            }
        });
        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),StaffManagement.class);
                intent.putExtra("profile","admin");
                startActivity(intent);

            }
        });
    }
}
