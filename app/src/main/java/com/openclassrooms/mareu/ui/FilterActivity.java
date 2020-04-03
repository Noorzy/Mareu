package com.openclassrooms.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.models.Reunion;

import java.util.List;

public class FilterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner filterSpinner;
    private String temp;
    private Button buttonReset;
    private Button buttonOk;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buttonReset = (Button) findViewById(R.id.button_filter_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = null;
            }
        });
        buttonOk = (Button) findViewById(R.id.button_filter_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(FilterActivity.this, MainActivity.class);
                mainActivity.putExtra("filter_text", temp);
                startActivity(mainActivity);


            }
        });

        filterSpinner = (Spinner) findViewById(R.id.filter_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.rooms, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);
        filterSpinner.setOnItemSelectedListener(this);




    }

    @Override
    public void finish() {
        Intent mainActivity = new Intent(FilterActivity.this, MainActivity.class);
        mainActivity.putExtra("filter_text", temp);
        this.startActivity(mainActivity);
        super.finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        temp = parent.getItemAtPosition(position).toString();
        temp = temp.toLowerCase().trim();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        temp = null;

    }
}
