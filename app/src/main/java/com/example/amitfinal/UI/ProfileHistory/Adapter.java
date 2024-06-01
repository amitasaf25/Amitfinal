package com.example.amitfinal.UI.ProfileHistory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.amitfinal.Repository.Moudle.LogShredPre;
import com.example.amitfinal.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<LogShredPre> infoList;

    // Constructor to receive the list of items
    public Adapter(List<LogShredPre> infoList) {
        this.infoList = infoList;
    }

    // Inflates the layout for each item in the RecyclerView
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_item_row, parent, false);
        return new ViewHolder(view);
    }

    // Binds data to the views in each item of the RecyclerView
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LogShredPre logShredPre = infoList.get(position);
        holder.nameTextView.setText(logShredPre.getDescrip());
        holder.timeTextView.setText(logShredPre.getDate());
    }

    // Returns the total number of items in the list
    @Override
    public int getItemCount() {
        return infoList.size();
    }

    // ViewHolder class to hold references to the views in each item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView timeTextView;

        // Constructor to initialize views
        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}
