package com.example.rzeposlaw.localizeme.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rzeposlaw.localizeme.R;
import com.example.rzeposlaw.localizeme.view.RozhaOneTextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mDatasetUsernames;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RozhaOneTextView username;
        public ImageButton openMapButton;

        public ViewHolder(View view) {
            super(view);
            username = (RozhaOneTextView) view.findViewById(R.id.username);
            openMapButton = (ImageButton) view.findViewById(R.id.open_map);
        }
    }

    public RecyclerViewAdapter(ArrayList<String> mDatasetUsernames) {
        this.mDatasetUsernames = mDatasetUsernames;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_friend, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.username.setText(mDatasetUsernames.get(position));
        holder.openMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO implement action on open map button click in row friend
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatasetUsernames.size();
    }
}

