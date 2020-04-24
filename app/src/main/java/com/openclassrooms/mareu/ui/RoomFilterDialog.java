package com.openclassrooms.mareu.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.openclassrooms.mareu.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class RoomFilterDialog extends DialogFragment {

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
                               ((MainActivity) getActivity()).filterList("salle a");
                               return;
                           case 2:
                               ((MainActivity) getActivity()).filterList("salle b");
                               return;
                           case 3:
                               ((MainActivity) getActivity()).filterList("salle c");
                               return;
                           case 4:
                               ((MainActivity) getActivity()).filterList("salle d");
                               return;
                           case 5:
                               ((MainActivity) getActivity()).filterList("salle e");
                               return;
                           case 6:
                               ((MainActivity) getActivity()).filterList("salle f");
                               return;
                           case 7:
                               ((MainActivity) getActivity()).filterList("salle g");
                               return;
                           case 8:
                               ((MainActivity) getActivity()).filterList("salle h");
                               return;
                           case 9:
                               ((MainActivity) getActivity()).filterList("salle i");
                               return;
                           case 10:
                               ((MainActivity) getActivity()).filterList("salle j");
                               return;
                           default:
                               ((MainActivity) getActivity()).filterList("");
                               return;
                       }
                    }
                });
        return builder.create();
    }
}
