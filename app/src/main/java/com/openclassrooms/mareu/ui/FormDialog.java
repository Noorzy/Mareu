package com.openclassrooms.mareu.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.models.SharedViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class FormDialog extends DialogFragment {
    private SharedViewModel viewModel;
    private EditText textTime;
    private EditText textDate;
    public static FormDialog newInstance(String setTime ,String setDate ) {
        FormDialog formDialog = new FormDialog();
        Bundle args = new Bundle();
        args.putString("Time", setTime);
        args.putString("Date", setDate);
        formDialog.setArguments(args);
        return formDialog;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String time = getArguments().getString("Time");
        String date = getArguments().getString("Date");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.form_dialog, null);
        builder.setView(view)
                .setTitle("Ajout de Réunion")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        Spinner roomSpinner = (Spinner) view.findViewById(R.id.room_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.rooms,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        roomSpinner.setAdapter(adapter);

        textDate = (EditText) view.findViewById(R.id.editText_date);
        textTime = (EditText) view.findViewById(R.id.editText_time);
        textDate.setText(date);
        textTime.setText(time);
        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getFragmentManager(), "date picker");


            }
        });
        textTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getFragmentManager(), "time picker");

            }
        });

        return builder.create();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textDate.setText(s);
            }
        });
        viewModel.getTime().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textTime.setText(s);

            }
        });
    }
}

