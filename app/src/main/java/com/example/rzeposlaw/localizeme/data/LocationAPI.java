package com.example.rzeposlaw.localizeme.data;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LocationAPI {

    @POST("/register")
    Call<User> register(@Body User user);

    @POST("/login")
    Call<User> login(@Body User user);

    @POST("/location")
    Call<Location> updateUserLocation(@Body LocationCommand locationCommand);

    @GET("/users")
    Call<ArrayList<Credentials>> getAllUsers();

    @GET("/location/{uid}")
    Call<Location> getUserLastLocation(@Path("uid") String uid);
}
