package com.openclassrooms.mareu.ui;

import android.os.Bundle;

import com.openclassrooms.mareu.DI.DI;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.models.Reunion;
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


    private RecyclerView recyclerview;
    private String nomReunion[] , nomSalle[];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getReunionApiService();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerview = (RecyclerView) findViewById(R.id.My_recyclerView);
        //nomReunion = getResources().getStringArray(R.array.Nom_reunion);
        //nomSalle = getResources().getStringArray(R.array.Nom_salle);
        mReunions = mApiService.getReunions();

        MyReunionListRecyclerViewAdapter myAdapter = new MyReunionListRecyclerViewAdapter(this, mReunions);
        recyclerview.setAdapter(myAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.addItemDecoration(new DividerItemDecoration(recyclerview.getContext(), DividerItemDecoration.VERTICAL));

    }

   //private void initList(){
   //    mReunions = mApiService.getReunions();
   //    recyclerview.setAdapter(new MyReunionListRecyclerViewAdapter(this, mReunions));
   //


   //}
}
