package com.paula.mypregnancy.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.paula.mypregnancy.R;

public class MainActivity extends AppCompatActivity {

    private Fragment mNewPregnancyFragment, mTrackPregnancyFragment;
    final FragmentManager fm = this.getSupportFragmentManager();
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment;

        if (savedInstanceState != null){
            fragment = fm.getFragment(savedInstanceState, String.valueOf(R.id.fragment_container));
        } else {
            fragment = fm.findFragmentById(R.id.fragment_container);
        }

        if (fragment == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_container, new NewPregnancyFragment())
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        getSupportFragmentManager().putFragment(outState,String.valueOf(R.id.fragment_container), fragment);
    }

    public void newPregnancy(){
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new NewPregnancyFragment());
        transaction.commit();
    }


    public void trackPregnancy(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new TrackPregnancyFragment());
        transaction.commit();
    }

}
