package com.example.quantumscan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

        // Initialize the ActivityResultLauncher for starting the QR code scanner
        startForResult =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(result.getResultCode(), result.getData());
                    if (scanResult != null) {
                        if (scanResult.getContents() == null) {
                            Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Scanned: " + scanResult.getContents(), Toast.LENGTH_LONG).show();
                            // TODO: Handle the scanned result here.
                        }
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

    /**
    跳转到Attendee_Eventpage
     */
    private void navigateToEventPage(Event event) {
        Intent intent = new Intent(getActivity(), AttendeeEventPage.class);
        intent.putExtra("event_id", event.getId()); // Pass event ID to the activity
        startActivity(intent);
    }
}
