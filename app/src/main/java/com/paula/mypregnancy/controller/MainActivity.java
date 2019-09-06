package com.paula.mypregnancy.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
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
    private Fragment mNewPregnancyFragment, mTrackPregnancyFragment, mMummyFragment, mBabyFragment;
    private String mFile = "myFile";
    private FragmentManager fm = this.getSupportFragmentManager();
    private final static String TAG = "Main Activity ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment;
        restoreDueDate();

        if (savedInstanceState != null) {
            fragment = fm.getFragment(savedInstanceState, String.valueOf(R.id.fragment_container));
        } else {
            fragment = fm.findFragmentById(R.id.fragment_container);
            if (mDueDate != null){
                trackPregnancy();
            } else {
                newPregnancy();
            }
        }

        if (fragment == null) {
            if (mDueDate != null){
                trackPregnancy();
            } else {
                newPregnancy();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        getSupportFragmentManager().putFragment(outState, String.valueOf(R.id.fragment_container), fragment);
    }

    private void restoreDueDate(){
        File[] files = getApplicationContext().getFilesDir().listFiles();
        if (files != null) {
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
            } catch (FileNotFoundException e) {
                Log.d(TAG, " file not found exception ");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d(TAG, " IO exception ");
                e.printStackTrace();
            }
        }
    }

    public void newPregnancy() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mNewPregnancyFragment = new NewPregnancyFragment();
        transaction.replace(R.id.fragment_container, mNewPregnancyFragment).addToBackStack("NEW");
        transaction.commit();
    }

    public void trackPregnancy() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mTrackPregnancyFragment = new TrackPregnancyFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("dueDate", mDueDate);
        mTrackPregnancyFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, mTrackPregnancyFragment).addToBackStack("TRACK");
        transaction.commit();
    }

    public void openMummyFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mMummyFragment = new MummyFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("dueDate", mDueDate);
        mMummyFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, mMummyFragment).addToBackStack("GALLERY");
        transaction.commit();
    }

    public void openBabyFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mBabyFragment = new BabyFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("dueDate", mDueDate);
        mBabyFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, mBabyFragment).addToBackStack("BABY_INFO");
        transaction.commit();
    }

    public void abortPregnancy() {
        popBackStack();
        File[] files = getApplicationContext().getFilesDir().listFiles();
        if (files != null)
            for (File file : files) {
                file.delete();
            }
        newPregnancy();
    }

    public void setDueDate(DueDate dueDate) {
        mDueDate = dueDate;
        saveDueDate();
        trackPregnancy();
    }

    private void saveDueDate() {
        Log.d(TAG, " store file ");
        String fileContents = Long.toString(mDueDate.getDueDateInMillis());
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(mFile, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            Log.d(TAG, " exception ");
            e.printStackTrace();
        }
    }

    private void popBackStack() {
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    @Override
    public void onBackPressed() {
        fm.popBackStack();
        Log.d(TAG, " back pressed ");
        int count = 0;
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            count++;
        }
        if (count == 1) {
            Log.d(TAG, "finishing ");
            finish();
        }
    }

    public void startCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 1);

    }
}
