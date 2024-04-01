package com.example.quantumscan;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.quantumscan.databinding.ActivityMapsBinding;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private String eventID;
    private String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        eventID = getIntent().getStringExtra("eventID");
        eventName = getIntent().getStringExtra("eventName");
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near UofA, Canada.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in our campus and move the camera
        LatLng uofa = new LatLng(53.524622005135996, -113.52469491976284);
        mMap.addMarker(new MarkerOptions()
                .position(uofa)
                .title("Marker in University of Alberta")
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker(90.0f)));  // this will be the marker for the last checked-in location of attendee
//        Admin admin = new Admin();
//        admin.retrieveEventAttendeeList(eventID, new Admin.OnEventAttendeeListRetrievedListener() {
//            @Override
//            public void onEventAttendeeListRetrieved(ArrayList<Attendee> attendeeList) {
//                for (Attendee attendee : attendeeList) {
//                    //LatLng attendeeLatlng = new LatLng( , );
//                    mMap.addMarker(new MarkerOptions().position(attendeeLatlng).title(attendee.getName()).draggable(false));  // this will be the marker for the last checked-in location of attendee
//                }
//            }
//        });
        mMap.moveCamera(CameraUpdateFactory.newLatLng(uofa));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(3));

        /*
        Todo:
        1. add "last checked-in location" in Attendee (which is the subcollection of the Event)
        2. iterate every attendee's location, get the latlng stats and make them the markers on map
        3. done
         */

    }
}