package com.example.rzeposlaw.localizeme.data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LocationAPI {

    @POST("/register")
    Call<User> register(@Body User user);

    @POST("/login")
    Call<User> login(@Body User user);

    @GET("/users")
    Call<ArrayList<Credentials>> getAllUsers();
}
