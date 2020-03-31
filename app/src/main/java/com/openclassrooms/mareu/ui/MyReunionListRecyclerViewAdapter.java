package com.openclassrooms.mareu.ui;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.openclassrooms.mareu.DI.DI;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.models.Reunion;
import com.openclassrooms.mareu.service.ReunionApiService;

import java.util.List;


public class MyReunionListRecyclerViewAdapter extends RecyclerView.Adapter<MyReunionListRecyclerViewAdapter.MyViewHolder> {
    Context context;
    private final List<Reunion> mReunions;
    private ReunionApiService mApiService;


    public MyReunionListRecyclerViewAdapter(Context ct, List<Reunion> items) {
        context = ct;
        mReunions = items;
        mApiService = DI.getReunionApiService();


    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Reunion reunion = mReunions.get(position);
        holder.nomReunion.setText(reunion.getName() + " -");
        holder.nomSalle.setText( reunion.getRoom() + " -");
        holder.timeReunion.setText(reunion.getTime());
        holder.emails.setText(reunion.getEmails());
        holder.myRowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailActivity = new Intent(v.getContext() , DetailActivity.class);
                detailActivity.putExtra("selectedMeeting" , reunion);
                v.getContext().startActivity(detailActivity);
            }
        });
        holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApiService.deleteReunion(reunion);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        });


    }

    @Override
    public int getItemCount() {
        return mReunions.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomReunion, nomSalle, timeReunion, emails;
        ConstraintLayout myRowLayout;
        ImageButton imageButtonDelete;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomReunion = itemView.findViewById(R.id.nom_reunion);
            nomSalle = itemView.findViewById(R.id.salle_reunion);
            timeReunion = itemView.findViewById(R.id.textView_time);
            emails = itemView.findViewById(R.id.textview_email_list);
            myRowLayout = itemView.findViewById(R.id.my_row_layout);
            imageButtonDelete = itemView.findViewById(R.id.imageButton_delete);


        }
    }
}

