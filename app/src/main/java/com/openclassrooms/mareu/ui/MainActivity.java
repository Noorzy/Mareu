package com.openclassrooms.mareu.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.mareu.DI.DI;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.models.Reunion;
import com.openclassrooms.mareu.models.SharedViewModel;
import com.openclassrooms.mareu.service.ReunionApiService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private ReunionApiService mApiService;
    private List<Reunion> mReunions;

    public SharedViewModel viewModel;

    private RecyclerView recyclerview;
    private String nomReunion[] , nomSalle[];
    public String Date = null;
    public String Time = null;




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
                showForm();

            }
        });

    }
    public  void showDetail(){
        DetailFragmentDialog detailDialog = new DetailFragmentDialog();
        detailDialog.show(getSupportFragmentManager() , "detail Dialog");
    }
    public void showForm(){
        FormDialog formDialog = FormDialog.newInstance(Time,Date);
        formDialog.show(getSupportFragmentManager(), "Form Dialog");

    }
    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        Date= currentDateString;
        viewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
        viewModel.setDate(Date);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Time = checkDigit(hourOfDay) + "H"+ checkDigit(minute);
        viewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
        viewModel.setTime(Time);


    }

    //private void initList(){
   //    mReunions = mApiService.getReunions();
   //    recyclerview.setAdapter(new MyReunionListRecyclerViewAdapter(this, mReunions));
   //


   //}
}
