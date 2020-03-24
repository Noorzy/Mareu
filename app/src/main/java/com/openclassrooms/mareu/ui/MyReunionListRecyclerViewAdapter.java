package com.openclassrooms.mareu.ui;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.models.Reunion;

import java.util.List;


public class MyReunionListRecyclerViewAdapter extends RecyclerView.Adapter<MyReunionListRecyclerViewAdapter.MyViewHolder>   {
    Context context;
    private final List<Reunion> mReunions;


    public MyReunionListRecyclerViewAdapter(Context ct, List<Reunion> items) {
        context = ct;
        mReunions = items;



    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Reunion reunion = mReunions.get(position);
        holder.nomReunion.setText(reunion.getName()+" -");
        holder.nomSalle.setText("salle "+ reunion.getRoom()+" -");
        holder.emails.setText(reunion.getEmails());
        holder.myRowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return mReunions.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomReunion, nomSalle , emails;
        ConstraintLayout myRowLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomReunion = itemView.findViewById(R.id.nom_reunion);
            nomSalle = itemView.findViewById(R.id.salle_reunion);
            emails = itemView.findViewById(R.id.textview_email_list);
            myRowLayout = itemView.findViewById(R.id.my_row_layout);


        }
    }
}

