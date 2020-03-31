package com.openclassrooms.mareu.ui;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.mareu.DI.DI;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.models.Reunion;
import com.openclassrooms.mareu.models.SharedViewModel;
import com.openclassrooms.mareu.service.ReunionApiService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private ReunionApiService mApiService;
    private List<Reunion> mReunions;
    private MyReunionListRecyclerViewAdapter adapter;
    private Reunion newReunion;

    public SharedViewModel viewModel;

    public RecyclerView recyclerview;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getReunionApiService();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerview = (RecyclerView) findViewById(R.id.My_recyclerView);
        mReunions = mApiService.getReunions();

        MyReunionListRecyclerViewAdapter myAdapter = new MyReunionListRecyclerViewAdapter(this, mReunions);
        recyclerview.setAdapter(myAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.addItemDecoration(new DividerItemDecoration(recyclerview.getContext(), DividerItemDecoration.VERTICAL));

        FloatingActionButton fab  = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent creationActivity = new Intent(v.getContext() , CreationActivity.class);
                startActivity(creationActivity);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        newReunion = getIntent().getParcelableExtra("new_meeting");
        if (newReunion != null) {
            mReunions.add(newReunion);
        }
        MyReunionListRecyclerViewAdapter myAdapter = new MyReunionListRecyclerViewAdapter(this, mReunions);
        recyclerview.setAdapter(myAdapter);


    }



 //   public void initList(){
 //      mReunions = mApiService.getReunions();
 //      recyclerview.setAdapter(new MyReunionListRecyclerViewAdapter(this, mReunions));
 //
 //  }
}
