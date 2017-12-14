package com.example.rzeposlaw.localizeme.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rzeposlaw.localizeme.R;
import com.example.rzeposlaw.localizeme.adapter.LoginRegisterPagerAdapter;
import com.example.rzeposlaw.localizeme.adapter.RecyclerViewAdapter;
import com.example.rzeposlaw.localizeme.data.Credentials;

import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Credentials> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        Intent intent = getIntent();
        names = intent.getStringArrayListExtra(LoginRegisterPagerAdapter.NAMES);

        //Bundle args = intent.getBundleExtra(LoginRegisterPagerAdapter.USERS_BUNDLE);
        //users = (ArrayList<Credentials>) args.getSerializable(LoginRegisterPagerAdapter.USERS);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter(names);
        mRecyclerView.setAdapter(mAdapter);
    }
}
