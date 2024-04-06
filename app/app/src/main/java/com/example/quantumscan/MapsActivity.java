package com.example.quantumscan;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.quantumscan.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private String eventID;
    private String eventName;
    private FusedLocationProviderClient fusedLocationClient;

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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
//        mMap.addMarker(new MarkerOptions()
//                .position(uofa)
//                .title("Marker in University of Alberta")
//                .draggable(false)
//                .icon(BitmapDescriptorFactory.defaultMarker(90.0f)));  // this will be the marker for the last checked-in location of attendee

        // retrieve the attendeeList of the event, and read the location of each attendee
        Admin admin = new Admin();
        admin.retrieveEventAttendeeList(eventID, new Admin.OnEventAttendeeListRetrievedListener() {
            @Override
            public void onEventAttendeeListRetrieved(ArrayList<Attendee> attendeeList) {
                for (Attendee attendee : attendeeList) {
                    if (attendee.isCheckedIn() && attendee.getLocation() != null) {  // only display the locations from checked-in attendees if locations exist
                        // get the location
                        double attendeeLatitude = attendee.getLocation().getLatitude();
                        double attendeeLongitude = attendee.getLocation().getLongitude();
                        LatLng attendeeLatlng = new LatLng(attendeeLatitude, attendeeLongitude);

                        // this will be the marker for the last checked-in location of attendee
                        mMap.addMarker(new MarkerOptions()
                                .position(attendeeLatlng)
                                .title(attendee.getName())
                                .draggable(false));
                    }

                }
            }
        });
        mMap.moveCamera(CameraUpdateFactory.newLatLng(uofa));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(3));

         // How to do with location permission check
        // Below is the if statement to check we we have permission for user location,
        // if we do (in the else block) then do something, if we don't then do something else (in if block)
        // This will be used in the Attendee check in, once the Attendee has checked in, we use this permission checking to decide if we put Geopoint object or null Object in firebase
        // Below is also an example of adding the user location marker on Google map depends on whether the use has allowed the permission for retrieving location
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            //return;
//        }
//        else {
//            fusedLocationClient.getLastLocation()
//                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            // Got last known location. In some rare situations this can be null.
//                            if (location != null) {
//                                // Logic to handle location object
//                                LatLng myLoc = new LatLng(location.getLatitude(), location.getLongitude());
//                                mMap.addMarker(new MarkerOptions()
//                                        .position(myLoc)
//                                        .title("Austin Meng")
//                                        .draggable(false)
//                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                            }
//                        }
//                    });
//        }




        /*
        Todo:
        1. add "last checked-in location" in Attendee (which is the subcollection of the Event)
        2. iterate every attendee's location, get the Latlng (Geopoint) stats and make them the markers on map
        3. done
        Tips: check something I've written above, there are some of my explanation.
         */

    }
}