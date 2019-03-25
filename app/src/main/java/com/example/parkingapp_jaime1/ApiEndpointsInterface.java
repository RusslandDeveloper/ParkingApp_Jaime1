package com.example.parkingapp_jaime1;

import com.example.parkingapp_jaime1.Model.ParkingLocationPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiEndpointsInterface {


    @GET("search?")
    Call<List<ParkingLocationPojo>>GetLocation(@Query("lat") String lat,
                                               @Query("lng") String lot);


}
