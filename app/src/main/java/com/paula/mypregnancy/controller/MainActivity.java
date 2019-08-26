package com.paula.mypregnancy.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.paula.mypregnancy.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button newPregnancy, trackPregnancy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){

        } else {

        }

        newPregnancy = findViewById(R.id.newPregnancy);
        newPregnancy.setOnClickListener(this);
        trackPregnancy = findViewById(R.id.trackPregnancy);
        trackPregnancy.setOnClickListener(this);

    }

    public void trackPregnancy(){

    }

    public void newPregnancy(){

    }

    public void removePregnancy(){

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.newPregnancy:
                intent = new Intent(this, NewPregnancyActivity.class);
                startActivity(intent);
                break;
            case R.id.trackPregnancy:
                intent = new Intent(this, TrackPregnancyActivity.class);
                break;
        }
        startActivity(intent);
    }
}
