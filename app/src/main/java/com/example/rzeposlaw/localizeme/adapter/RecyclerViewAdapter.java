package com.example.rzeposlaw.localizeme.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rzeposlaw.localizeme.R;
import com.example.rzeposlaw.localizeme.view.RozhaOneTextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private String[] mDatasetUsernames;
    private String[] mDatasetEmails;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RozhaOneTextView username;
        public RozhaOneTextView email;
        public ViewHolder(View view) {
            super(view);
            username = (RozhaOneTextView) view.findViewById(R.id.username);
            email = (RozhaOneTextView) view.findViewById(R.id.email);
        }
    }

    public RecyclerViewAdapter(String[] mDatasetUsernames, String[] mDatasetEmails) {
        this.mDatasetUsernames = mDatasetUsernames;
        this.mDatasetEmails = mDatasetEmails;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_friend, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.username.setText(mDatasetUsernames[position]);
        holder.email.setText(mDatasetEmails[position]);
    }

    @Override
    public int getItemCount() {
        return mDatasetUsernames.length;
    }
}

