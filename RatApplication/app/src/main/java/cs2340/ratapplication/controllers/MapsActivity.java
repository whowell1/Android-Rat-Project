package cs2340.ratapplication.controllers;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import cs2340.ratapplication.R;
import cs2340.ratapplication.models.DatabaseHelper;
import cs2340.ratapplication.models.Sighting;

import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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



    // changed SIGHTINGID FROM LONG TO STRIN

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        Sighting listOfMarkers[] = dbHelper.get10sightings();


        try {
    //
            Sighting listOfMarkers[] = dbHelper.get10sightings();
            for (int i = 0; i <= listOfMarkers.length; i++) {
                LatLng individualMarkers = new LatLng(listOfMarkers[i].latitude, listOfMarkers[i].longitude);
                mMap.addMarker(new MarkerOptions().position(individualMarkers).title(String.valueOf("sighting id: " + listOfMarkers[i].sightingID)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(individualMarkers));
            }


        }

        catch (Exception e) {
            System.out.println(e);
        }
    }






}



