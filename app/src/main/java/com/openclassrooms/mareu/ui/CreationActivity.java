package com.openclassrooms.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.models.Reunion;
import com.openclassrooms.mareu.models.SharedViewModel;
import com.openclassrooms.mareu.service.EmailValidator;
import com.openclassrooms.mareu.service.ReunionApiService;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class CreationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
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
    private Button okButton;
    private Button cancelButton;
    public String roomSelected;
    public String Date = null;
    public String Time = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        okButton = (Button) findViewById(R.id.button_creation_ok);
        cancelButton = (Button) findViewById(R.id.button_creation_cancel);
        textName = (EditText) findViewById(R.id.editText_name);
        textDate = (EditText) findViewById(R.id.editText_date);
        textTime = (EditText) findViewById(R.id.editText_time);

        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        textTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        Spinner roomSpinner = (Spinner) findViewById(R.id.room_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.rooms, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        roomSpinner.setAdapter(adapter);
        roomSpinner.setOnItemSelectedListener(this);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = (textName.getText().toString());
                String r = roomSelected ;
                String d = (textDate.getText().toString());
                String t = (textTime.getText().toString());
                String e = (textViewEmail.getText().toString());
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence toastText;
                if (n.equals("") || r.equals("") || d.equals("") || t.equals("") || e.equals("")){
                    toastText = "Veullez remplir tous les champs";
                    Toast toast = Toast.makeText(context, toastText, duration);
                    toast.show();
                    return;
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
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textViewEmail = (TextView) findViewById(R.id.textView_email);
        imageButton_add_email = (ImageButton) findViewById(R.id.imageButton_add_email);
        textValidator = (TextView) findViewById(R.id.textView_validator);
        textMail = (EditText) findViewById(R.id.editText_email);

        textMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EmailValidator emailValidator = new EmailValidator();
                if (emailValidator.validator(s)){
                    imageButton_add_email.setEnabled(true);
                    imageButton_add_email.setColorFilter(Color.parseColor("#008577"));
                    //imageButton_add_email.setColorFilter(Color.GREEN);
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
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
    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}
