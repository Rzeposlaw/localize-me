package com.example.rzeposlaw.localizeme.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rzeposlaw.localizeme.R;
import com.example.rzeposlaw.localizeme.adapter.RecyclerViewAdapter;
import com.example.rzeposlaw.localizeme.data.ApiClient;
import com.example.rzeposlaw.localizeme.data.Credentials;
import com.example.rzeposlaw.localizeme.data.LocationAPI;
import com.example.rzeposlaw.localizeme.data.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendListActivity extends AppCompatActivity {

    private LocationAPI apiService =
            ApiClient.getClient().create(LocationAPI.class);

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Credentials> users;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Call<List<Credentials>> call = apiService.getAllUsers();
        call.enqueue(new Callback<List<Credentials>>() {
            @Override
            public void onResponse(Call<List<Credentials>> call, Response<List<Credentials>> response) {
                users = response.body();
            }

            @Override
            public void onFailure(Call<List<Credentials>> call, Throwable t) {

            }
        });

        List<String> names = new ArrayList<>();
        for (Credentials user : users){
            names.add(user.getUsername());
        }

        mAdapter = new RecyclerViewAdapter(names);
        mRecyclerView.setAdapter(mAdapter);
    }
}
