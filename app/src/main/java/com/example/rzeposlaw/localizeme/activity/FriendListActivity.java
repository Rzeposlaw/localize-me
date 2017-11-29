package com.example.rzeposlaw.localizeme.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rzeposlaw.localizeme.R;
import com.example.rzeposlaw.localizeme.adapter.RecyclerViewAdapter;

public class FriendListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //TODO get dataset of users

        //mAdapter = new RecyclerViewAdapter(names, emails);
        //TODO add dataset to adapter
        mRecyclerView.setAdapter(mAdapter);
    }
}
