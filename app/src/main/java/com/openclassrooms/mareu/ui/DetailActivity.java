package com.openclassrooms.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.models.Reunion;

public class DetailActivity extends AppCompatActivity {
    private Reunion selectedMeeting;
    public TextView detailNom;
    public TextView detailRoom;
    public TextView detailDate;
    public TextView detailTime;
    public TextView detailEmails;
    public CardView detailCardview;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detail);
        selectedMeeting = getIntent().getParcelableExtra("selectedMeeting");
        detailNom = (TextView) findViewById(R.id.textView_detail_nom);
        detailRoom = (TextView) findViewById(R.id.textView_detail_room);
        detailDate = (TextView) findViewById(R.id.textView_detail_date);
        detailTime = (TextView) findViewById(R.id.textView_detail_time);
        detailEmails = (TextView) findViewById(R.id.textView_detail_emails);
        detailCardview = (CardView) findViewById(R.id.detail_cardview);
        detailNom.setText(selectedMeeting.getName());
        detailRoom.setText(selectedMeeting.getRoom());
        detailDate.setText(selectedMeeting.getDate());
        detailTime.setText(selectedMeeting.getTime());
        detailEmails.setText(selectedMeeting.getEmails());






    }
}
