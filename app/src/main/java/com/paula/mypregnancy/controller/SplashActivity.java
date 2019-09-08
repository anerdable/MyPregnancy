package com.paula.mypregnancy.controller;
/*
 * SplashActivity
 *
 * An implementation of a pregnancy tracker app.
 * Development of mobile applications
 * Umeå Universitet, summer course 2019
 *
 * Paula D'Cruz
 *
 * This is one of the controller classes. It controls the splash activity that is shown when the application starts.
 *
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    /**
     * onCreate
     *
     * this is a splash activity shown only for a few seconds when opening the application, and then finishing.
     *
     * @param savedInstanceState saved state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
