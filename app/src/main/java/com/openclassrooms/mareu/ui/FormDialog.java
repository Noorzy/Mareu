package com.openclassrooms.mareu.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.openclassrooms.mareu.DI.DI;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.models.Reunion;
import com.openclassrooms.mareu.models.SharedViewModel;
import com.openclassrooms.mareu.service.EmailValidator;
import com.openclassrooms.mareu.service.ReunionApiService;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class FormDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private MyReunionListRecyclerViewAdapter adapter;
    private List<Reunion> mReunions;
    private ReunionApiService mApiService;
    private SharedViewModel viewModel;
    private Reunion reunion;
    private EditText textName;
    private EditText textTime;
    private EditText textDate;
    private EditText textMail;
    private TextView textValidator;
    private ImageButton imageButton_add_email;
    private TextView textViewEmail;
    public String roomSelected;
    public static FormDialog newInstance(String setTime ,String setDate ) {
        FormDialog formDialog = new FormDialog();
        Bundle args = new Bundle();
        args.putString("Time", setTime);
        args.putString("Date", setDate);
        formDialog.setArguments(args);
        return formDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        viewModel.setDate(null);
        viewModel.setTime(null);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mApiService = DI.getReunionApiService();
        mReunions = mApiService.getReunions();
        final String time = getArguments().getString("Time");
        final String date = getArguments().getString("Date");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.form_dialog, null);
        builder.setView(view)
                .setTitle("Ajout de Réunion")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.setDate(null);
                        viewModel.setTime(null);

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String n = (textName.getText().toString());
                        String r = roomSelected ;
                        String d = (textDate.getText().toString());
                        String t = (textTime.getText().toString());
                        String e = (textViewEmail.getText().toString());

                        reunion = new Reunion(1,n,r,d,t,e);
                        //mReunions.add(reunion);

                       //reunion.setName(textName.getText().toString());
                       // //TODO reunion.setRoom();
                       //reunion.setDate(textDate.getText().toString());
                       //reunion.setTime(textTime.getText().toString());
                       //reunion.setEmails(textViewEmail.getText().toString());


                        mReunions = mApiService.getReunions();
                        mApiService.addReunion(reunion);
                        adapter = new MyReunionListRecyclerViewAdapter(getActivity(),mReunions);
                        adapter.notifyDataSetChanged();




                    }
                });
        Spinner roomSpinner = (Spinner) view.findViewById(R.id.room_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.rooms,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        roomSpinner.setAdapter(adapter);
        roomSpinner.setOnItemSelectedListener(this);

        textName = (EditText) view.findViewById(R.id.editText_name);
        textDate = (EditText) view.findViewById(R.id.editText_date);
        textTime = (EditText) view.findViewById(R.id.editText_time);
        //textDate.setText(date);
        //textTime.setText(time);
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
        textViewEmail = (TextView) view.findViewById(R.id.textView_email);
        imageButton_add_email = (ImageButton) view.findViewById(R.id.imageButton_add_email);
        textValidator = (TextView) view.findViewById(R.id.textView_validator);
        textMail = (EditText) view.findViewById(R.id.editText_email);
        textMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
               // if (s.toString().matches(regex)){
               //     imageButton_add_email.setEnabled(true);
               //     //imageButton_add_email.setColorFilter(Color.parseColor("#27f178"));
               //     imageButton_add_email.setColorFilter(Color.GREEN);
               //     textValidator.setTextColor(Color.parseColor("#5eba7d"));
               //     textValidator.setText("Email Valide");
               // }else{
               //     imageButton_add_email.setEnabled(false);
               //     imageButton_add_email.setColorFilter(Color.parseColor("#b4b4b4"));
               //     textValidator.setTextColor(Color.parseColor("#cc0000"));
               //     textValidator.setText("Email Invalide");
               // }
                EmailValidator emailValidator = new EmailValidator();
                if (emailValidator.validator(s)){
                    imageButton_add_email.setEnabled(true);
                         //imageButton_add_email.setColorFilter(Color.parseColor("#27f178"));
                         imageButton_add_email.setColorFilter(Color.GREEN);
                         textValidator.setTextColor(Color.parseColor("#5eba7d"));
                         textValidator.setText("Email Valide");
                }else{
                    imageButton_add_email.setEnabled(false);
                         imageButton_add_email.setColorFilter(Color.parseColor("#b4b4b4"));
                         textValidator.setTextColor(Color.parseColor("#cc0000"));
                         textValidator.setText("Email Invalide");
                }




            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imageButton_add_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textValidator.getText() == "Email Valide"){
                    String temptxt = textViewEmail.getText().toString();
                    textViewEmail.setText(textMail.getText() + " , " + temptxt   );
                    textMail.setText(null);
                }
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         roomSelected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

