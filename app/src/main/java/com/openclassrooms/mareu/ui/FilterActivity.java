package com.openclassrooms.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.openclassrooms.mareu.R;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
