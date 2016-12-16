// Team Arrow:
// Harpreet Singh
// Luis Esquivel
// Sagar Ranipa

package com.arrow.arrowhc;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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



public class SearchActivity extends AppCompatActivity {
TabLayout tabLayout;ArrayList<HashMap<String,String>> arayList;
    ViewPager viewPager;RequestQueue requestQueue;Button search1;ImageButton search2;EditText getfragment_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);

        search1=(Button)findViewById(R.id.by_n);
        search2=(ImageButton)findViewById(R.id.by_d);
        requestQueue= Volley.newRequestQueue(getBaseContext());
        arayList=new ArrayList<>();
        viewPager=(ViewPager)findViewById(R.id.vpager);

        viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(),getApplicationContext()));
        tabLayout=(TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
    }

    private class CustomAdapter extends FragmentPagerAdapter {
        private String fragments[]={"BY NAME","BY DEPT"};

        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
           switch(position)
           {
               case 0:
                   return new Fragment1();
               case 1:
                   return new Fragment2();
               default:
                   return null;
           }
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
           return fragments[position];

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_transition,R.anim.activity_closescale);
    }
}
