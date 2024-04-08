package com.example.quantumscan;




import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

/**
 * In Attendee_Eventpage.java. Attendee_Eventpage.java you need to create three functions "View Information", "Recive Notification "Recive Notification" and "Scan QR-code".
 * Click View Information to view the event information.
 * Click Recive Notification to receive notifications
 * Click on Scan QR-code for check in.
 */

public class AttendeeEventPage extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String eventId; // This should be passed from the previous activity
    private String eventName;

    private TextView tvEventTitle;// TextView for displaying event title

    private TextView tvEventDescription;// TextView for displaying event description
    private ImageView imageViewEventPoster;

    private FireStoreBridge fireStoreBridge;

    private Button buttonReturn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_eventpage);

        eventId = getIntent().getStringExtra("eventID");
        String eventName = getIntent().getStringExtra("eventName");

        TextView eventNameView = findViewById(R.id.textView_eventName);
        eventNameView.setText(eventName);

        fireStoreBridge = new FireStoreBridge("EVENT");
        View btnViewInfo = findViewById(R.id.btnViewInformation);//Main Activity??or main menu? View Information
        View btnReceiveNotification = findViewById(R.id.btnReceiveNotification);//Main Activity??or main menu? ReceiveNotification

        View btnScanQRCode = findViewById(R.id.btnScanQRCode);//Main Activity??or main menu? ScanQRCode
        buttonReturn = findViewById(R.id.returnButton);

        btnViewInfo.setOnClickListener(view -> switchToDetailsView());// Set up the "View Information" button
        btnReceiveNotification.setOnClickListener(view -> switchToNotificationView());// Set up the "ReceiveNotification" button
        btnScanQRCode.setOnClickListener(view -> switchToScanQRCode());// Set up the "btnScanQRCode" button

        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to MainActivity
                finish();
            }
        });

    }

    private void switchToDetailsView() { //  This view information interface?or menu connects to each component of textview.xml.

        setContentView(R.layout.textview);

        //  findViewById will reference views from textview.xml
        tvEventTitle = findViewById(R.id.tvEventTitle);
        tvEventDescription = findViewById(R.id.tvEventDescription);
        imageViewEventPoster = findViewById(R.id.ivEventBackground);
        Button ivReturn = findViewById(R.id.ivReturn);
        ivReturn.setOnClickListener(v -> switchToMainView()); // Switch back to the main view


        fetchEventInformation(eventId);

    }
    private void switchToNotificationView() {
        Intent detailIntent = new Intent(AttendeeEventPage.this, OrganizerNotification.class);
        detailIntent.putExtra("eventID", eventId);
        detailIntent.putExtra("eventName", eventName);
        startActivity(detailIntent);

    }

    private void fetchEventInformation(String eventID) {
        // Use FireStoreBridge to retrieve the event
        fireStoreBridge.retrieveEvent(eventID, new FireStoreBridge.OnEventRetrievedListener() {
            @Override
            public void onEventRetrieved(ArrayList<Event> eventList, ArrayList<String> organizerList) {

                if (!eventList.isEmpty()) {
                    Event event = eventList.get(0);// Assuming the first item is the event we're interested in
                    tvEventTitle = findViewById(R.id.tvEventTitle);
                    tvEventDescription = findViewById(R.id.tvEventDescription);
                    imageViewEventPoster = findViewById(R.id.ivEventBackground);

                    tvEventTitle.setText(event.getTitle());
                    tvEventDescription.setText(event.getDescription());
                    imageDisplay(eventId, imageViewEventPoster);


                } else {

                    Toast.makeText(AttendeeEventPage.this, "Event not found." + eventID, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void imageDisplay(String EventID, ImageView imageView){
        FireStoreBridge fb_events = new FireStoreBridge("EVENT");
        fb_events.displayImage(EventID, imageView);
    }







    private void switchToScanQRCode() { //  The page of this connects to every component of theScanQRCode.xml

    }

    private void switchToMainView() {
        // Switch back to the main view
        setContentView(R.layout.activity_attendee_eventpage);
        // Re-bind the button since we've switched the layout
        View btnViewInfo = findViewById(R.id.btnViewInformation);
        btnViewInfo.setOnClickListener(view -> switchToDetailsView());
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
