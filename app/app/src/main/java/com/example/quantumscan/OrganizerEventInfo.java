package com.example.quantumscan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrganizerEventInfo extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizer_event_info);
        Button backButton = findViewById(R.id.returnButton);
        Button shareButton = findViewById(R.id.shareButton);
        TextView titleView = findViewById(R.id.title_textView);


        String eventID = getIntent().getStringExtra("eventID");
        titleView.setText(eventID);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent detailIntent = new Intent(OrganizerEventInfo.this, OrganizerEventShare.class);
                detailIntent.putExtra("eventID", eventID);
                startActivity(detailIntent);
            }
        });

    }
}
