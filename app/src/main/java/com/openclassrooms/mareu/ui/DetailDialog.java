package com.openclassrooms.mareu.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.models.Reunion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

public class DetailDialog extends DialogFragment {

    private Reunion selectedMeeting;
    private TextView detailNom;
    private TextView detailRoom;
    private TextView detailDate;
    private TextView detailTime;
    private TextView detailEmails;
    private CardView detailCardview;
    private ImageButton closeButton;

    public static DetailDialog newInstance(Reunion selectedMeeting ) {
        DetailDialog detailDialog = new DetailDialog();
        Bundle args = new Bundle();
        args.putParcelable("selected meeting" , selectedMeeting);
        detailDialog.setArguments(args);
        return detailDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = (inflater.inflate(R.layout.detail_dialog, null));
        selectedMeeting = getArguments().getParcelable("selected meeting");
        builder.setView(view);

        detailNom = (TextView) view.findViewById(R.id.textView_detail_nom);
        detailRoom = (TextView) view.findViewById(R.id.textView_detail_room);
        detailDate = (TextView) view.findViewById(R.id.textView_detail_date);
        detailTime = (TextView) view.findViewById(R.id.textView_detail_time);
        detailEmails = (TextView) view.findViewById(R.id.textView_detail_emails);
        detailCardview = (CardView) view.findViewById(R.id.detail_cardview);
        closeButton = (ImageButton) view.findViewById(R.id.imageButton_detail_close);
        detailNom.setText(selectedMeeting.getName());
        detailRoom.setText(selectedMeeting.getRoom());
        detailDate.setText(selectedMeeting.getDate());
        detailTime.setText(selectedMeeting.getTime());
        detailEmails.setText(selectedMeeting.getEmails());

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }
}
