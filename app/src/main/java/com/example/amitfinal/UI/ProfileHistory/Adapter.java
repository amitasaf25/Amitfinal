package com.example.amitfinal.UI.ProfileHistory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.amitfinal.Repository.Moudle.LogShredPre;
import com.example.amitfinal.R;

import java.util.List;

public class Adapter extends  RecyclerView.Adapter<Adapter.ViewHolder>
{
    private List<LogShredPre> infoList;

    // בנאי לקבלת רשימת הפריטים
    public Adapter(List<LogShredPre> infoList) {
        this.infoList = infoList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LogShredPre logShredPre = infoList.get(position);
        holder.nameTextView.setText(logShredPre.getDescrip());
        holder.timeTextView.setText(logShredPre.getDate());
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView nameTextView;
        public TextView timeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}
