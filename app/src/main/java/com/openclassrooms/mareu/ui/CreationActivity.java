package com.openclassrooms.mareu.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.models.Reunion;
import com.openclassrooms.mareu.models.SharedViewModel;
import com.openclassrooms.mareu.service.EmailValidator;

import java.text.DateFormat;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


public class CreationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private SharedViewModel viewModel;
    private Reunion reunion;



    private ImageButton imageButton_add_email;
    private TextView textViewEmail;
    private Button okButton;
    private Button cancelButton;
    public String roomSelected;
    public String Date = null;
    public String Time = null;
    private TextInputLayout textInputName;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputDate;
    private TextInputLayout textInputTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        createMeeting();
        addEmailToEmailList();
        openDatePicker();
        openTimePicker();
        validateEmail();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        viewModel.getDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textInputDate.getEditText().setText(s);
            }
        });
        viewModel.getTime().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textInputTime.getEditText().setText(s);
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        Date= currentDateString;
        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        viewModel.setDate(Date);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Time = checkDigit(hourOfDay) + "H"+ checkDigit(minute);
        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        viewModel.setTime(Time);
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    private boolean validateName(){
        String nameInput = (textInputName.getEditText().getText().toString());
        if (nameInput.isEmpty()){
            textInputName.setError("Veuillez remplir le champ");
            return false;
        }else{
            textInputName.setError(null);
            return true;
        }
    }

    private boolean validateDate(){
        String dateInput = (textInputDate.getEditText().getText().toString());
        if (dateInput.isEmpty()){
            textInputDate.setError("Veuillez choisir une date");
            return false;
        }else{
            textInputDate.setError(null);
            return true;
        }
    }

    private boolean validateTime(){
        String timeInput = (textInputTime.getEditText().getText().toString());
        if (timeInput.isEmpty()){
            textInputTime.setError("Veuillez choisir une heure");
            return false;
        }else{
            textInputTime.setError(null);
            return true;
        }
    }

    private void init(){
        setContentView(R.layout.activity_creation);
        okButton = findViewById(R.id.button_creation_ok);
        cancelButton = findViewById(R.id.button_creation_cancel);
        textInputName = findViewById(R.id.text_input_name);
        textInputEmail= findViewById(R.id.text_input_email);
        textInputDate = findViewById(R.id.text_input_date);
        textInputTime = findViewById(R.id.text_input_time);
        Spinner roomSpinner = (Spinner) findViewById(R.id.room_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.rooms, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        roomSpinner.setAdapter(adapter);
        roomSpinner.setOnItemSelectedListener(this);
        textViewEmail = (TextView) findViewById(R.id.textView_email);
        imageButton_add_email = (ImageButton) findViewById(R.id.imageButton_add_email);
        imageButton_add_email.setEnabled(false);
    }

    private void createMeeting(){
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n =(textInputName.getEditText().getText().toString());                       //nom réunion
                String r = roomSelected ;                                                           //Salle
                String d = (textInputDate.getEditText().getText().toString());                      //Date
                String t = (textInputTime.getEditText().getText().toString());                      //Heure
                String e = (textViewEmail.getText().toString());                                    //Emails
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence toastText;
                if (!validateName() | r.equals("") | r.equals("Salle...")| !validateDate() | !validateTime() | e.equals("")){
                    toastText = "Veullez remplir tous les champs";
                    Toast toast = Toast.makeText(context, toastText, duration);
                    toast.show();
                }else {
                    reunion = new Reunion(1, n, r, d, t, e);
                    toastText = "Réunion créée";
                    Toast toast = Toast.makeText(context, toastText,duration);
                    toast.show();
                    Intent mainActivity = new Intent(v.getContext(), MainActivity.class);
                    mainActivity.putExtra("new_meeting", reunion);
                    v.getContext().startActivity(mainActivity);
                }
            }
        });
    }

    private void addEmailToEmailList(){
        imageButton_add_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textInputEmail.getError() == null){
                    String temptxt = textViewEmail.getText().toString();
                    textViewEmail.setText(textInputEmail.getEditText().getText() + " , " + temptxt);
                    textInputEmail.getEditText().setText(null);
                    textInputEmail.setError(null);
                }
            }
        });
    }

    private void openDatePicker(){
        textInputDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    private void openTimePicker(){
        textInputTime.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
    }

    private void validateEmail(){
        textInputEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EmailValidator emailValidator = new EmailValidator();
                if (emailValidator.validator(s)){
                    imageButton_add_email.setEnabled(true);
                    imageButton_add_email.setColorFilter(Color.parseColor("#008577"));
                    textInputEmail.setError(null);

                }else{

                    imageButton_add_email.setEnabled(false);
                    imageButton_add_email.setColorFilter(Color.parseColor("#b4b4b4"));
                    textInputEmail.setError("Email Invalide");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (textInputEmail.getEditText().getText() == null){
                    textInputEmail.setError(null);
                }

            }
        });
    }
}
