package com.example.saferoad;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private Fragment fragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment=new map();
                    fragmentcall();
                    return true;
                case R.id.navigation_dashboard:
                    fragment=new map();
                    fragmentcall();
                    return true;
                case R.id.navigation_notifications:
                    fragment=new map();
                    fragmentcall();
                    return true;
            }
            return false;
        }
    };
    public void fragmentcall(){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.mainfrag,fragment);
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(findViewById(R.id.mainfrag)!=null){
            if(savedInstanceState!=null){
                return;
            }
            map h=new map();
            h.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.mainfrag,h).commit();
        }
    }

}
