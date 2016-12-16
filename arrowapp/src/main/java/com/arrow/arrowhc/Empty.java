package com.arrow.arrowhc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Empty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
          String profile=getIntent().getStringExtra("profile");
        Intent intent=new Intent(getBaseContext(),StaffManagement.class);
        intent.putExtra("profile",profile);
        startActivity(intent);
    }
}
