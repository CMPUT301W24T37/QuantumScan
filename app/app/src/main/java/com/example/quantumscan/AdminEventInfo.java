package com.example.quantumscan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class AdminEventInfo extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_event_info);
        Button backButton = findViewById(R.id.returnButton);
        TextView titleView = findViewById(R.id.title_textView);
        TextView infoView = findViewById(R.id.info_textView);
        ImageView imageView = findViewById(R.id.background_imageView);


        String eventID = getIntent().getStringExtra("eventID");
        String eventName = getIntent().getStringExtra("eventName");
        titleView.setText(eventName);
        this.setInfo(eventID, infoView);
        this.imageDisplay(eventID, imageView);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void setInfo(String eventID,TextView infoView){
        FireStoreBridge fb_events = new FireStoreBridge("EVENT");
        fb_events.retrieveAllEvent(new FireStoreBridge.OnEventRetrievedListener() {
            @Override
            public void onEventRetrieved(ArrayList<Event> events, ArrayList<String> organizerList) {

                for(Event event: events){
                    if(Objects.equals(eventID, event.getId())){
                        infoView.setText(event.getDescription());
                    }
                }
            }

        });
    }

    public void updateInfo(String eventID,String newInfo){
        FireStoreBridge fb_events = new FireStoreBridge("EVENT");
        fb_events.retrieveEvent(eventID,new FireStoreBridge.OnEventRetrievedListener() {
            @Override
            public void onEventRetrieved(ArrayList<Event> event, ArrayList<String> organizerList) {
                Event thisEvent = event.get(0);
                fb_events.updateEventDescription(thisEvent.getId(), newInfo);
            }
        });
    }

    public void imageDisplay(String EventID, ImageView imageView){
        FireStoreBridge fb_events = new FireStoreBridge("EVENT");
        fb_events.displayImage(EventID, imageView);
    }

}
