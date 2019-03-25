package com.example.parkingapp_jaime1.Presenter;


import android.util.Log;

import com.example.parkingapp_jaime1.ApiEndpointsInterface;
import com.example.parkingapp_jaime1.Model.ParkingLocationPojo;
import com.example.parkingapp_jaime1.R;
import com.example.parkingapp_jaime1.Views.MapsActivity;
import com.example.parkingapp_jaime1.Views.ViewContract;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Presenter implements PresenterContract {

    private static final String TAG = "Presenter";



    // Objects initializing Retrofit and the ViewContract
    Retrofit retrofit;
    ViewContract view;

    // Constructor
    public Presenter (ViewContract view ){this.view = view;}


    @Override
    public void initializeRetrofit() {

        // Creating an instance of retrofit
        retrofit = new Retrofit.Builder().
                baseUrl(view.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    @Override
    public void getLocation() {


        // Test for locating Atlanta
        final String latitude ="33.753746",
                longitude = "-84.386330";



        ApiEndpointsInterface apiEndpointsInterface = retrofit.create(ApiEndpointsInterface.class);
        apiEndpointsInterface.GetLocation(MapsActivity.Slatitude, MapsActivity.Slongitude).enqueue(new Callback<List<ParkingLocationPojo>>() {
            @Override
            public void onResponse(Call<List<ParkingLocationPojo>> call, Response<List<ParkingLocationPojo>> response) {


                if(response.body() != null) {

                    MapsActivity.mMap.clear();

                    if(response.body().size() >= 5){

                        for(int i = 0; 5 > i ;i++  ) {

                            /*  // Unused for now

                            view.fillData(response.body().get(i).lat,
                                    response.body().get(i).lng,
                                    response.body().get(i).name);
                                    */

                            Double dLat = Double.valueOf(response.body().get(i).lat);
                            Double dLng = Double.valueOf(response.body().get(i).lng);
                            LatLng newposition = new LatLng(dLat,dLng);


                            MapsActivity.mMap.addMarker(new MarkerOptions().position(newposition).
                                    title(response.body().get(i).name));



                            if(response.body().size() == 4){

                                float z = 14.5f;

                                MapsActivity.mMap.moveCamera(CameraUpdateFactory.newLatLng(newposition));
                                MapsActivity.mMap.moveCamera(CameraUpdateFactory.zoomTo(z));

                            }



                                /* For testing purposes, leave these logcat here
                                Log.i(TAG, " ###  Latitude " + response.body().get(i).lat);
                                Log.i(TAG, " ###  Longitude " + response.body().get(i).lng );
                                Log.i(TAG, " ### Name " + response.body().get(i).name );
                                */
                        }





                    }else{


                        Log.i(TAG, " ###  List is too short! -");


                    }




                }else {
                    Log.i(TAG, " ###  Is failing dude!!! No body on response! -");
            }
                }

            @Override
            public void onFailure(Call<List<ParkingLocationPojo>> call, Throwable t) {
                view.showError(t.getMessage());
            }







        });

    }
}
