package com.example.quantumscan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

public class OrganizerEventInfo extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizer_event_info);
        Button backButton = findViewById(R.id.returnButton);
        Button shareButton = findViewById(R.id.shareButton);
        Button editInfo = findViewById(R.id.buttonEdit);
        TextView titleView = findViewById(R.id.title_textView);
        TextView infoView = findViewById(R.id.info_textView);
        EditText editText = findViewById(R.id.editText);
        Button confirmButton  = findViewById(R.id.buttonConfirm);
        ImageView imageView = findViewById(R.id.background_imageView);

        String eventID = getIntent().getStringExtra("eventID");
        String eventName = getIntent().getStringExtra("eventName");
        titleView.setText(eventName);
        this.setInfo(eventID,infoView);
        this.imageDisplay(eventID, imageView);


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

        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmButton.getVisibility() == View.GONE) {
                    confirmButton.setVisibility(View.VISIBLE); // Show the button
                    editText.setVisibility(View.VISIBLE);
                } else {
                    confirmButton.setVisibility(View.GONE); // Hide the button
                    editText.setVisibility(View.GONE);
                }

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newInfo = editText.getText().toString(); // Get the text from EditText
                if (!newInfo.isEmpty()) {
                    updateInfo(eventID,newInfo);

                }
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
