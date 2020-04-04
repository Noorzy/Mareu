package com.openclassrooms.mareu.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.openclassrooms.mareu.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DateFilterDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Filtre par Date")
                .setItems(R.array.rooms, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       switch(which){
                           case 1:
                               ((MainActivity) getActivity()).FilterList("salle a");
                               return;
                           case 2:
                               ((MainActivity) getActivity()).FilterList("salle b");
                               return;
                           case 3:
                               ((MainActivity) getActivity()).FilterList("salle c");
                               return;
                           case 4:
                               ((MainActivity) getActivity()).FilterList("salle d");
                               return;
                           case 5:
                               ((MainActivity) getActivity()).FilterList("salle e");
                               return;
                           case 6:
                               ((MainActivity) getActivity()).FilterList("salle f");
                               return;
                           case 7:
                               ((MainActivity) getActivity()).FilterList("salle g");
                               return;
                           case 8:
                               ((MainActivity) getActivity()).FilterList("salle h");
                               return;
                           case 9:
                               ((MainActivity) getActivity()).FilterList("salle i");
                               return;
                           case 10:
                               ((MainActivity) getActivity()).FilterList("salle j");
                               return;
                           default:
                               ((MainActivity) getActivity()).FilterList("");
                               return;
                       }
                    }
                });


        return builder.create();
    }
}
