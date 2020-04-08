package com.openclassrooms.mareu.ui;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.openclassrooms.mareu.DI.DI;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.models.Reunion;
import com.openclassrooms.mareu.service.ReunionApiService;

import java.util.ArrayList;
import java.util.List;


public class MyReunionListRecyclerViewAdapter extends RecyclerView.Adapter<MyReunionListRecyclerViewAdapter.MyViewHolder> implements Filterable {
    Context context;
    public List<Reunion> mReunions;
    private  List<Reunion> reunionsFull;
    private ReunionApiService mApiService;






    public MyReunionListRecyclerViewAdapter(Context ct, List<Reunion> items) {
        context = ct;
        mReunions = items;
        mApiService = DI.getReunionApiService();
        reunionsFull = new ArrayList<>(mReunions);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Reunion reunion = mReunions.get(position);
        holder.nomReunion.setText(reunion.getName() + " -");
        holder.nomSalle.setText( reunion.getRoom() + " -");
        holder.timeReunion.setText(reunion.getTime());
        holder.emails.setText(reunion.getEmails());
        holder.myRowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)v.getContext()).ShowDetail(reunion);


            }
        });
        holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApiService.deleteReunion(reunion);
                reunionsFull.remove(reunion);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
                UpdateData(mApiService.getReunions());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mReunions.size();
    }

    @Override
    public Filter getFilter() {
        return reunionsFilter;
    }

    private Filter reunionsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Reunion> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(reunionsFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase();
                for (Reunion item : reunionsFull){
                    if (item.getRoom().toLowerCase().matches(filterPattern)){
                        filteredList.add(item);
                    }else{
                        if (item.getDate().toLowerCase().matches(filterPattern)){
                            filteredList.add(item);
                        }
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mReunions.clear();
            mReunions.addAll((List)results.values);
            notifyDataSetChanged();
            if (mReunions.size() == 0){
                ((MainActivity)context).ShowText();
            }else{
                ((MainActivity)context).HideText();
            }
        }
    };


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomReunion, nomSalle, timeReunion, emails;
        ConstraintLayout myRowLayout;
        ImageButton imageButtonDelete;
        CardView myRowCardview;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomReunion = itemView.findViewById(R.id.nom_reunion);
            nomSalle = itemView.findViewById(R.id.salle_reunion);
            timeReunion = itemView.findViewById(R.id.textView_time);
            emails = itemView.findViewById(R.id.textview_email_list);
            myRowLayout = itemView.findViewById(R.id.my_row_layout);
            imageButtonDelete = itemView.findViewById(R.id.imageButton_delete);
            myRowCardview = itemView.findViewById(R.id.my_row_cardview);


        }
    }
    public void UpdateData(List<Reunion> data){
        mReunions = new ArrayList<>();
        mReunions.addAll(data);
        notifyDataSetChanged();


    }
    public void UpdateReunionsfull(List<Reunion> data){
        reunionsFull = new ArrayList<>();
        reunionsFull.addAll(data);
        notifyDataSetChanged();

    }
}

