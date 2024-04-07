package com.example.quantumscan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrganizerEventPage extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizer_event_homepage);

        TextView eventNameView = findViewById(R.id.textView_eventName);
        Button backButton = findViewById(R.id.returnButton);
        Button infoButton = findViewById(R.id.buttonInfo);
        Button listButton = findViewById(R.id.buttonViewAttend);
        Button posterButton = findViewById(R.id.buttonPoster);
        Button locationButton = findViewById(R.id.buttonLocation);
        Button notification = findViewById(R.id.buttonSendNote);

        // Retrieve the city name passed from MainActivity
        String eventID = getIntent().getStringExtra("eventID");
        String eventName = getIntent().getStringExtra("eventName");
        eventNameView.setText(eventName);

        /*
        Event eventOBJ = DataHolder.getInstance().getEvent(eventID);
        System.out.println("Title:"+ eventOBJ.getTitle());

         */

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to MainActivity
                finish();
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(OrganizerEventPage.this, OrganizerEventInfo.class);
                detailIntent.putExtra("eventID", eventID);
                detailIntent.putExtra("eventName", eventName);
                startActivity(detailIntent);
            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(OrganizerEventPage.this, OrganizerViewAttendees.class);
                detailIntent.putExtra("eventID", eventID);
                detailIntent.putExtra("eventName", eventName);
                startActivity(detailIntent);
            }
        });

        posterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(OrganizerEventPage.this, OrganizerUpdatePoster.class);
                detailIntent.putExtra("eventID", eventID);
                detailIntent.putExtra("eventName", eventName);
                startActivity(detailIntent);
            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(OrganizerEventPage.this, MapsActivity.class);
                detailIntent.putExtra("eventID", eventID);
                detailIntent.putExtra("eventName", eventName);
                startActivity(detailIntent);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(OrganizerEventPage.this, OrganizerNotification.class);
                detailIntent.putExtra("eventID", eventID);
                detailIntent.putExtra("eventName", eventName);
                startActivity(detailIntent);
            }
        });
    }

}
