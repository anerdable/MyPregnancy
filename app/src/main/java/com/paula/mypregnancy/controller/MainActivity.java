package com.paula.mypregnancy.controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.paula.mypregnancy.R;
import com.paula.mypregnancy.model.DueDate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private DueDate mDueDate;
    private Fragment mNewPregnancyFragment, mTrackPregnancyFragment;
    private String mFile = "myFile";
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment;
        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState != null){
            fragment = fm.getFragment(savedInstanceState, String.valueOf(R.id.fragment_container));
        } else {
            fragment = fm.findFragmentById(R.id.fragment_container);
        }

        if (fragment == null) {
            if (mFile != null){
                try {
                    FileInputStream fis = openFileInput(mFile);
                    InputStreamReader inputStreamReader = new InputStreamReader(fis);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line = bufferedReader.readLine();
                    long milliSeconds = Long.parseLong(line);
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(milliSeconds);
                    c.add(Calendar.DATE, -280);
                    mDueDate = new DueDate(c);
                    Log.d(TAG, "" + mDueDate.toString());
                    inputStreamReader.close();
                    trackPregnancy(mDueDate);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                newPregnancy();
            }
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
        Log.d(TAG, " new pregnancy ");
        mNewPregnancyFragment = new NewPregnancyFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mNewPregnancyFragment);
        transaction.commit();
    }

    public void trackPregnancy(DueDate dueDate){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mTrackPregnancyFragment = new TrackPregnancyFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("dueDate", dueDate);
        mTrackPregnancyFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, mTrackPregnancyFragment).addToBackStack(null);
        transaction.commit();
    }

    public void abortPregnancy(){
        File[] files = getApplicationContext().getFilesDir().listFiles();
        if(files != null)
            for(File file : files) {
                file.delete();
            }
        newPregnancy();
    }

    public void setDueDate(DueDate dueDate){
        mDueDate = dueDate;
        storeFile();
        trackPregnancy(mDueDate);
    }

    public void storeFile(){
        Log.d(TAG, " store file ");
        String fileContents = Long.toString(mDueDate.getDueDateInMillis());
        FileOutputStream outputStream;

        try {
            Log.d(TAG, " trying ");
            outputStream = openFileOutput(mFile, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            Log.d(TAG, " file contents " + fileContents);
            outputStream.close();
        } catch (Exception e) {
            Log.d(TAG, " exception ");
            e.printStackTrace();
        }
    }

    private void deleteFile(){

    }

}
