package com.example.quantumscan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< Updated upstream
import android.widget.LinearLayout;
=======
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
>>>>>>> Stashed changes
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class AttendeeFragment extends Fragment {

    private ActivityResultLauncher<String> requestCameraPermissionLauncher;
    private ActivityResultLauncher<Intent> startForResult;

    private RecyclerView recyclerViewEvents;
    private EventAdapter eventAdapter;
    private ArrayList<Event> events; // Placeholder for your events data


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
        startForResult =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(result.getResultCode(), result.getData());
                    if (scanResult != null && scanResult.getContents() != null) {
                        String eventID = scanResult.getContents();
                        FireStoreBridge fb = new FireStoreBridge("EVENTS"); // 请确保与您的 Firestore 集合名称匹配
                        fb.retrieveEvent(eventID, new FireStoreBridge.OnEventRetrievedListener() {
                            @Override
                            public void onEventRetrieved(ArrayList<Event> events, ArrayList<String> organizerList) {
                                if (!events.isEmpty()) {
                                    Event event = events.get(0); // 假设每个 eventID 对应唯一事件
                                    System.out.println(event.getTitle());
                                    updateUIWithEventDetails(event);
                                } else {
                                    Toast.makeText(getContext(), "Event not found", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "No QR code scanned", Toast.LENGTH_LONG).show();
                    }
                });


        // Initialize your events list here
        events = new ArrayList<>();
        // Example: events.add(new Event("1", "Event Title", "Event Description"));
    }




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendee, container, false);

        LinearLayout scanQRCodeButton = view.findViewById(R.id.buttonScanQR);
        scanQRCodeButton.setOnClickListener(v -> startQRCodeScanner());

        recyclerViewEvents = view.findViewById(R.id.recyclerViewEvents);
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the adapter with the events list and set it to the RecyclerView
        eventAdapter = new EventAdapter(getContext(), events, event -> navigateToEventPage(event));
        recyclerViewEvents.setAdapter(eventAdapter);

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

    private void updateUIWithEventDetails(Event event) {
        System.out.println(event.getTitle());
        View view = getView();
        if (view != null) {
            ImageView imageViewEvent = view.findViewById(R.id.imageViewEvent);
            TextView textViewEventTitle = view.findViewById(R.id.textViewEventTitle);
            TextView textViewEventDescription = view.findViewById(R.id.textViewEventDescription);

            textViewEventTitle.setText(event.getTitle());
            textViewEventDescription.setText(event.getDescription());


            // 使用 Picasso 或其他库加载图片。这里假设 posterCode 是图片的 URL
            //Picasso.get().load(event.getPosterCode()).into(imageViewEvent);
        }
    }


    /**
    跳转到Attendee_Eventpage
     */
    private void navigateToEventPage(Event event) {
        Intent intent = new Intent(getActivity(), AttendeeEventPage.class);
        intent.putExtra("event_id", event.getId()); // Pass event ID to the activity
        startActivity(intent);
    }
}
