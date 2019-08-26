package com.paula.mypregnancy.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.paula.mypregnancy.R;
import com.paula.mypregnancy.model.DueDate;

public class NewPregnancyActivity extends AppCompatActivity{

    private Button dueDate, trackPregnancy;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pregnancy);
        if (savedInstanceState != null){

        } else {

        }
        dueDate = findViewById(R.id.calculateDueDate);
        trackPregnancy = findViewById(R.id.track);
        toolbar = findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);
        trackPregnancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DueDate dueDate = new DueDate(0,0,0);
                Intent intent = new Intent(NewPregnancyActivity.this, TrackPregnancyActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }


}
