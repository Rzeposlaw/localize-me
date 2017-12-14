package com.example.rzeposlaw.localizeme.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rzeposlaw.localizeme.R;
import com.example.rzeposlaw.localizeme.view.RozhaOneTextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mDatasetUsernames;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RozhaOneTextView username;
        public ViewHolder(View view) {
            super(view);
            username = (RozhaOneTextView) view.findViewById(R.id.username);
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
    }

    @Override
    public int getItemCount() {
        return mDatasetUsernames.size();
    }
}

