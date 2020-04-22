/*
    Mareu - Application de gestion de réunions
    OpenClassrooms DA Projet 4

 */

package com.openclassrooms.mareu.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.mareu.DI.DI;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.models.Reunion;
import com.openclassrooms.mareu.service.ReunionApiService;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ReunionApiService mApiService;
    private List<Reunion> mReunions;
    private Reunion newReunion;

    public MyReunionListRecyclerViewAdapter myAdapter;
    public RecyclerView recyclerview;
    public RoomFilterDialog roomDialog;
    private TextView textNoMeeting;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.filter_by_room:
                Toast.makeText(this,"Filtre par salle",  Toast.LENGTH_SHORT).show();
                roomDialog.show(getSupportFragmentManager(),"room dialog");
                return true;
            case R.id.filter_by_date:
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                return true;
            case R.id.filter_reset:
                Toast.makeText(this,"Reset Filtre",  Toast.LENGTH_SHORT).show();
                FilterList(null);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getReunionApiService();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
                                                                                                    //region RecyclerView
        recyclerview = (RecyclerView) findViewById(R.id.My_recyclerView);
        mReunions = mApiService.getReunions();
        myAdapter = new MyReunionListRecyclerViewAdapter( mReunions);
        recyclerview.setAdapter(myAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.addItemDecoration(new DividerItemDecoration(recyclerview.getContext(), DividerItemDecoration.VERTICAL));
                                                                                                    //endregion
        textNoMeeting = findViewById(R.id.textView_no_meeting);
                                                                                                    //region FAB
        FloatingActionButton fab  = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent creationActivity = new Intent(v.getContext() , CreationActivity.class);
                startActivity(creationActivity);
            }
        });
                                                                                                    //endregion
        roomDialog = new RoomFilterDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
        newReunion = getIntent().getParcelableExtra("new_meeting");
        if (newReunion != null) {
            if (mReunions.contains(newReunion) == false) {
                mApiService.addReunion(newReunion);
                myAdapter.UpdateData(mReunions);
                myAdapter.UpdateReunionsfull(mReunions);
                newReunion = null;
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {                   // Filter the List according to the Date picked in the DatePickerFragment
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        FilterList(currentDateString);
    }

    public void FilterList(String constraint){
        myAdapter.getFilter().filter(constraint);
    }

    public void ShowDetail(Reunion reunion){
        DetailDialog detailDialog = DetailDialog.newInstance(reunion);
        detailDialog.show(getSupportFragmentManager(), "detail dialog");
    }

    public void ShowText(){
        textNoMeeting.setVisibility(TextView.VISIBLE);
    }

    public void HideText(){
        textNoMeeting.setVisibility(TextView.INVISIBLE);
    }

}
