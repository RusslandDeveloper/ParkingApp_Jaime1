    package com.example.parkingapp_jaime1.Views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Geocoder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp_jaime1.Presenter.Presenter;
import com.example.parkingapp_jaime1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , ViewContract {

    private static final String TAG = "MapsActivity";


      Double latitude = 33.753746, longitude = -84.386330;

    public static  String Slatitude , Slongitude ;


    public static GoogleMap mMap;




    // Constant of the base URL of the API
    public static String Base_URL ;


    // Views
    LinearLayout topLayout;
    SeekBar seekBar;
    TextView seekbartext;
    EditText lat,lng;
    Button searchbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        findIdVariables();


        seekbartext.setText(  Integer.toString(seekBar.getProgress()) );

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                seekbartext.setText(Integer.toString(seekBar.getProgress()) );
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        topLayout =  findViewById(R.id.topLayout);

        //Getting the URL
        Base_URL = getString(R.string.baseURL);




        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                        switch (menuItem.getItemId()){

                            case R.id.menu_search:
                                topLayout.setVisibility(LinearLayout.VISIBLE);
                                float z = 1.0f;
                                MapsActivity.mMap.animateCamera((CameraUpdateFactory.zoomTo(z)));


                                break;

                            case R.id.menu_find:
                                topLayout.setVisibility(LinearLayout.GONE);


                                break;
                            case R.id.menu_reservation:
                                topLayout.setVisibility(LinearLayout.GONE);

                                break;
                            case R.id.menu_mycar:
                                topLayout.setVisibility(LinearLayout.GONE);

                                break;
                        }

                        return false;
                    }
                }
        );




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        initializeButtonSearch();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng atlanta = new LatLng(latitude, longitude );


        mMap.addMarker(new MarkerOptions().position(atlanta).title("Marker in Atlanta"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(atlanta));
    }

    @Override
    public String getBaseURL() {
        return Base_URL;
    }

    @Override
    public void fillData(String lat, String lng, String name) {



    }

    @Override
    public void showError(String errorMessage) {


        Toast.makeText(MapsActivity.this, "FAIL CONNECTION!!", Toast.LENGTH_LONG).show();
    }


    private void findIdVariables(){
        seekBar = findViewById(R.id.seekbar_time);
        seekbartext = findViewById(R.id.txtV_seekbarTrack);
        lat = findViewById(R.id.txtE_latitude);
        lng = findViewById(R.id.txtE_longitude);
        searchbtn = findViewById(R.id.btn_searchMycar);

    }


    private void initializeButtonSearch(){

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Log.i(TAG, " ###  Button is working!-");

                if(!lat.getText().toString().equals("") &&
                        !lng.getText().toString().equals("")){

                    // Get text from the latitude text boxes
                    Slatitude = lat.getText().toString();
                    Slongitude= lng.getText().toString();


                    // Initialize retrofit and get location
                    Presenter presenter = new Presenter(MapsActivity.this);
                    presenter.initializeRetrofit();
                    presenter.getLocation();


                    lat.setText("");
                    lng.setText("");
                    topLayout.setVisibility(LinearLayout.GONE);



                }else {


                    initalizeToastCoordinatesFail();
                    lat.setText("");
                    lng.setText("");

                }
            }
        });
    }


    private void initalizeToastCoordinatesFail(){

        Context context = getApplicationContext();
        CharSequence text = "Introduce coordinates correctly...";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }



    }
