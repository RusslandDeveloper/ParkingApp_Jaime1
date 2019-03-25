package com.example.parkingapp_jaime1.Views;

public interface ViewContract {


    // Get BaseURL
    String getBaseURL();

    void fillData(String lat, String lng, String name);

    // Show errors message
    void showError(String errorMessage);


}
