package com.example.quantumscan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminEventPage extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_event_homepage);

        TextView eventNameView = findViewById(R.id.textView_eventName);
        Button backButton = findViewById(R.id.returnButton);
        Button infoButton = findViewById(R.id.buttonInfo);
        Button posterButton = findViewById(R.id.buttonPoster);
        Button deleteButton = findViewById(R.id.buttonDelete);
        Admin admin = new Admin();

        // Retrieve the city name passed from MainActivity
        String eventID = getIntent().getStringExtra("eventID");
        String eventName = getIntent().getStringExtra("eventName");
        eventNameView.setText(eventName);
        System.out.println("ID" + eventID);


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
                Intent detailIntent = new Intent(AdminEventPage.this, OrganizerEventInfo.class);
                detailIntent.putExtra("eventID", eventID);
                detailIntent.putExtra("eventName", eventName);
                startActivity(detailIntent);
            }
        });

//        listButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent detailIntent = new Intent(AdminEventPage.this, OrganizerViewAttendees.class);
//                detailIntent.putExtra("eventID", eventID);
//                detailIntent.putExtra("eventName", eventName);
//                startActivity(detailIntent);
//            }
//        });
//
        posterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.deleteEventPoster(eventID);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                admin.removeEvent(eventID);
            }
        });
    }

}
