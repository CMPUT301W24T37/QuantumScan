package com.example.quantumscan;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * In Attendee_Eventpage.java. Attendee_Eventpage.java you need to create three functions "View Information", "Recive Notification "Recive Notification" and "Scan QR-code".
 * Click View Information to view the event information.
 * Click Recive Notification to receive notifications
 * Click on Scan QR-code for check in.
 */

public class AttendeeEventPage extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String eventId; // This should be passed from the previous activity

    private TextView tvEventTitle;// TextView for displaying event title

    private TextView tvEventDescription;// TextView for displaying event description


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_eventpage);

        eventId = getIntent().getStringExtra("event_id");
        View btnViewInfo = findViewById(R.id.btnViewInformation);//Main Activity??or main menu? View Information

        View btnReceiveNotification = findViewById(R.id.btnReceiveNotification);//Main Activity??or main menu? ReceiveNotification

        View btnScanQRCode = findViewById(R.id.btnScanQRCode);//Main Activity??or main menu? ScanQRCode




        btnViewInfo.setOnClickListener(view -> switchToDetailsView());// Set up the "View Information" button
        btnReceiveNotification.setOnClickListener(view -> switchToNotificationView());// Set up the "ReceiveNotification" button
        btnScanQRCode.setOnClickListener(view -> switchToScanQRCode());// Set up the "btnScanQRCode" button
    }

    private void switchToDetailsView() { //  This view information interface?or menu connects to each component of textview.xml.

        setContentView(R.layout.textview);

        // Now findViewById will reference views from textview.xml
        tvEventTitle = findViewById(R.id.tvEventTitle);
        tvEventDescription = findViewById(R.id.tvEventDescription);
        Button ivReturn = findViewById(R.id.ivReturn);
        ivReturn.setOnClickListener(v -> switchToMainView()); // Switch back to the main view

        fetchEventInformation();

    }


    private void switchToNotificationView() { //  This receives every component of the notification page/interface/menu? (.xml)
        // Switch to the notification view
        //setContentView(R.layout.notification_view);

        // Initialize components specific to notification_view.xml
        // Assume you have a return button with the id btnReturnFromNotification
        //Button btnReturnFromNotification = findViewById(R.id.btnReturnFromNotification);
        //btnReturnFromNotification.setOnClickListener(v -> switchToMainView());

        // Here, you would load and display notification-related information
    }


    private void switchToScanQRCode() { //  The page of this connects to every component of theScanQRCode.xml

    }

    private void switchToMainView() {
        // Switch back to the main view
        setContentView(R.layout.activity_attendee_eventpage);
        // Re-bind the button since we've switched the layout
        View btnViewInfo = findViewById(R.id.btnViewInformation);
        btnViewInfo.setOnClickListener(view -> switchToDetailsView());
        Button btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(v -> finish());
    }

    private void fetchEventInformation() {
        // Assume eventId is already initialized
        DocumentReference docRef = db.collection("events").document(eventId);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String title = documentSnapshot.getString("title");
                String description = documentSnapshot.getString("description");

                // Set the fetched information to the TextViews
                tvEventTitle.setText(title);
                tvEventDescription.setText(description);
            } else {
                Toast.makeText(AttendeeEventPage.this, "Event data not found.", Toast.LENGTH_SHORT).show();
                switchToMainView(); // Switch back if data is not found
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(AttendeeEventPage.this, "Error fetching event details.", Toast.LENGTH_SHORT).show();
            switchToMainView(); // Switch back on failure
        });
    }



    private void receiveNotification() {
        // Implementation would depend on how you handle notifications.
        // This could involve Firestore listeners for real-time updates or Firebase Cloud Messaging.
    }

    private void scanQRCodeForCheckIn() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(false);
        integrator.setPrompt("Scan QR Code for Check-In");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            // Handle successful scan
            updateEventCheckInStatus(eventId, true);
        } else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
        }
    }

    private void updateEventCheckInStatus(String eventId, boolean isCheckedIn) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference eventRef = db.collection("events").document(eventId);
        eventRef.update("isCheckedIn", isCheckedIn)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(AttendeeEventPage.this, "Check-In status updated successfully.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AttendeeEventPage.this, "Failed to update Check-In status.", Toast.LENGTH_SHORT).show();
                });
    }
}
