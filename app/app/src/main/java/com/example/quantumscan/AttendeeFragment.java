package com.example.quantumscan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;



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

    // Initialize the launcher with the contract and callback to handle the result
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    if (data != null) {
                        // Handle the data from the result
                        String eventID = data.getStringExtra("eventID");
                        String eventName = data.getStringExtra("eventName");
                        // Use the returned value as needed
                        dataList.add(eventName);
                        eventIDList.add(eventID);
                        eventAdapter.notifyDataSetChanged();


                    }
                }
            });




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        startForResult =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(result.getResultCode(), result.getData());
                    if (scanResult != null) {
                        if (scanResult.getContents() == null) {
                            Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Scanned: " + scanResult.getContents(), Toast.LENGTH_LONG).show();

                            Intent detailIntent = new Intent(getActivity(), EventInformationFragment.class);
                            detailIntent.putExtra("eventID", scanResult.getContents());
                            detailIntent.putExtra("userID", UserID);
                            mStartForResult.launch(detailIntent);
                        }
                    }
//


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
        fb.retrieveUser("1658f5315ca1a74e", new FireStoreBridge.OnUserRetrievedListener() {
            @Override
            public void onUserRetrieved(User user, ArrayList<String> attendeeRoles, ArrayList<String> organizerRoles) {
                for(String event : attendeeRoles){
                    eventIDList.add(event);
                    System.out.println(event);
                }

                FireStoreBridge fb_events = new FireStoreBridge("EVENT");
                fb_events.retrieveAllEvent(new FireStoreBridge.OnEventRetrievedListener() {
                    @Override
                    public void onEventRetrieved(ArrayList<Event> events, ArrayList<String> organizerList) {
                        for(String eventID : eventIDList){

                            for(Event event: events){
                                if(Objects.equals(eventID, event.getId())){
                                    System.out.println("Size"+ event.getTitle());
                                    dataList.add(event.getTitle());
                                }
                            }
                        }
                        eventAdapter.notifyDataSetChanged();

                    }
                });

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


}
