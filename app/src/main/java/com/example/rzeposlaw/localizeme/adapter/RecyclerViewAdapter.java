package com.example.rzeposlaw.localizeme.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.rzeposlaw.localizeme.R;
import com.example.rzeposlaw.localizeme.activity.MapActivity;
import com.example.rzeposlaw.localizeme.data.Credentials;
import com.example.rzeposlaw.localizeme.view.RozhaOneTextView;
import com.github.siyamed.shapeimageview.mask.PorterImageView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static final String LOGGED_USER_ID = "LOGGED_USER_ID";
    public static final String FRIENDS_ID = "FRIENDS_ID";

    private ArrayList<Credentials> mDatasetUsers;
    private Context mContext;
    private long loggedUserId;
    private Long friendsId;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public RozhaOneTextView username;
        public ImageButton openMapButton;
        public RozhaOneTextView email;
        public PorterImageView userAvatar;

        public ViewHolder(View view) {
            super(view);
            username = (RozhaOneTextView) view.findViewById(R.id.username);
            openMapButton = (ImageButton) view.findViewById(R.id.open_map);
            email = (RozhaOneTextView) view.findViewById(R.id.email);
            userAvatar = (PorterImageView) view.findViewById(R.id.user_avatar);
        }
    }

    public RecyclerViewAdapter(ArrayList<Credentials> mDatasetUsers, long loggedUserId, Context context) {
        this.mDatasetUsers = mDatasetUsers;
        this.loggedUserId = loggedUserId;
        mContext = context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_friend, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.username.setText(mDatasetUsers.get(position).getUsername());
        holder.openMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendsId = mDatasetUsers.get(position).getId();
                openMapActivity();
            }
        });
        holder.email.setText(mDatasetUsers.get(position).getEmail());
        if(mDatasetUsers.get(position).getSex().equals("W")){
            holder.userAvatar.setImageDrawable(mContext.getResources().getDrawable(R.drawable.woman_avatar));
        }
        else{
            holder.userAvatar.setImageDrawable(mContext.getResources().getDrawable(R.drawable.man_avatar));
        }
    }

    private void openMapActivity(){
        Intent intent = new Intent(mContext, MapActivity.class);
        intent.putExtra(LOGGED_USER_ID, loggedUserId);
        intent.putExtra(FRIENDS_ID, friendsId);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mDatasetUsers.size();
    }
}

