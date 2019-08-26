package com.paula.mypregnancy.controller;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.paula.mypregnancy.R;
import com.paula.mypregnancy.model.DueDate;

public class TrackPregnancyActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private static final String DUE_DATE_PARCEL = "com.paula.mypregnancy.model.DueDate";
    private DueDate mDueDate;
    private TextView mPregnancyDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_pregnancy);
        if (savedInstanceState != null){
            mDueDate = savedInstanceState.getParcelable(DUE_DATE_PARCEL);
        } else {

        }
        mToolbar = findViewById(R.id.action_bar);
        mPregnancyDay = findViewById(R.id.pregnancyDay);
        setSupportActionBar(mToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
        return super.onOptionsItemSelected(item);
    }
}
