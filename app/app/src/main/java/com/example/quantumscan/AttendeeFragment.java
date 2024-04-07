package com.example.quantumscan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Objects;

public class AttendeeFragment extends Fragment {

    private ActivityResultLauncher<String> requestCameraPermissionLauncher;
    private ActivityResultLauncher<Intent> startForResult;

    private ListView eventListView;
    //private EventAdapter eventAdapter;
    private ArrayList<Event> events; // Placeholder for your events data


    private ArrayAdapter<String> eventAdapter;
    private ArrayList<String> attendeeRole;
    private String id;
    private String UserID;

    private ArrayList<String> dataList;
    private ArrayList<String> eventIDList;
    private FusedLocationProviderClient fusedLocationClient;
    private Location location = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getContext());
        if (this.getActivity() == null) {Log.d("atmeng", "Activity is null smh");}

        // Initialize the ActivityResultLauncher for camera permission request
        requestCameraPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        startQRCodeScanner();
                    } else {
                        Toast.makeText(getContext(), "Camera permission is needed to scan QR codes", Toast.LENGTH_SHORT).show();
                    }
                });

        // Initialize the ActivityResultLauncher for starting the QR code scanner
        System.out.println("RIGHT BEFORE SCAN =========================================================================");

        startForResult =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    System.out.println("IDK----------------------------------------------------------------------------------");
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(result.getResultCode(), result.getData());
                    if (scanResult != null) {
                        if (scanResult.getContents() == null) {
                            Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Scanned: " + scanResult.getContents(), Toast.LENGTH_LONG).show();
                            //TODO: CHECKIN JOININ
                            FireStoreBridge fb2 = new FireStoreBridge("USER");
                            UserID = getCurrentUserId();
                            fb2.checkAttendeeExist(UserID, scanResult.getContents(), new FireStoreBridge.OnUserCheckInListener() {
                                @Override
                                public void onCheckUserJoin(boolean attendeeExist) {
                                    System.out.println(attendeeExist);
                                    // sign up
                                    if (attendeeExist == false) {
                                        System.out.println("checked doesnt exist");
                                        Intent detailIntent = new Intent(getActivity(), EventInformationFragment.class);
                                        detailIntent.putExtra("eventID", scanResult.getContents());
                                        detailIntent.putExtra("userID", UserID);
                                        startActivity(detailIntent);
                                        // check in
                                    } else {
                                        System.out.println("checked  exist");
                                        fb2.updateAttendeeCheckIn(UserID, scanResult.getContents());
                                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                            Log.d("atmeng", "Try to get location and update it");
                                            fusedLocationClient.getLastLocation()
                                                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                                        @Override
                                                        public void onSuccess(Location location) {
                                                            Log.d("atmeng", "get location query success!");

                                                            // Got last known location. In some rare situations this can be null.
                                                            if (location != null) {
                                                                Log.d("atmeng", "Lat: "+String.valueOf(location.getLatitude())+"Lon: "+String.valueOf(location.getLongitude()));
                                                                // Logic to handle location object
                                                                fb2.updateAttendeeLocation(UserID, scanResult.getContents(), location);
                                                            } else {
                                                                Log.d("atmeng", "location is null...");
                                                                fb2.updateAttendeeLocation(UserID, scanResult.getContents(), location);
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                }
                            });

                        }
                    }
                });

        // Initialize your events list here
        events = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendee, container, false);

        LinearLayout scanQRCodeButton = view.findViewById(R.id.buttonScanQR);
        scanQRCodeButton.setOnClickListener(v -> startQRCodeScanner());

        eventListView = view.findViewById(R.id.attendeeEventList);
        dataList = new ArrayList<>();
        eventIDList = new ArrayList<>();

        eventAdapter = new ArrayAdapter<>(view.getContext(), R.layout.event_content, dataList);

        FireStoreBridge fb = new FireStoreBridge("USER");

        fb.retrieveJoinedEvent(getCurrentUserId(), new FireStoreBridge.OnRetrieveJoinedEvent() {
            @Override
            public void onRetrieveJoinedEvent(ArrayList<EventFireBaseHolder> eventList) {
                dataList.clear();
                for(EventFireBaseHolder event: eventList){
                    if (!dataList.contains(event.getId())) {
                        dataList.add(event.getTitle());
                        eventIDList.add(event.getId());

                    }
                }
                eventAdapter.notifyDataSetChanged();
            }
        });

        eventListView.setAdapter(eventAdapter);


        /**
         跳转到Attendee_Eventpage
         */

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedEventName = dataList.get(position);
                String selectedEventID = eventIDList.get(position);
                Intent detailIntent = new Intent(getActivity(), AttendeeEventPage.class);
                detailIntent.putExtra("eventID", selectedEventID);
                detailIntent.putExtra("eventName", selectedEventName);
                startActivity(detailIntent);
            }
        });


        return view;
    }

    private void startQRCodeScanner() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            IntentIntegrator integrator = new IntentIntegrator(getActivity());
            Intent intent = integrator.createScanIntent();
            startForResult.launch(intent);
        } else {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }
    private String getCurrentUserId() {
        String userId = Settings.Secure.getString(this.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return userId;

    }


}