package com.example.quantumscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;


import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;



import java.util.ArrayList;


import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class EventInformationFragment extends AppCompatActivity {
    private TextView textViewEventTitle;
    private TextView textViewEventDescription;
    private ImageView imageViewEventPoster;
    private Button buttonJoinEvent;
    private Button buttonReturn;
    private String eventId;
    private FireStoreBridge fireStoreBridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_event_information);

        textViewEventTitle = findViewById(R.id.textViewEventTitle);
        textViewEventDescription = findViewById(R.id.textViewEventDescription);

        buttonJoinEvent = findViewById(R.id.buttonJoinEvent);
        buttonReturn = findViewById(R.id.buttonReturn);
        imageViewEventPoster = findViewById(R.id.imageViewEvent);





        // Retrieve the event ID passed from AttendeeFragment

        eventId = getIntent().getStringExtra("eventID");


        buttonJoinEvent.setOnClickListener(v -> joinEvent(eventId));
        fireStoreBridge = new FireStoreBridge("EVENT");

        // 使用事件ID从Firestore检索事件信息
        fetchEventInformation(eventId);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 关闭当前活动，返回上一个活动
            }
        });
    }

    private void fetchEventInformation(String eventId) {
        // 使用FireStoreBridge检索事件信息
        fireStoreBridge.retrieveEvent(eventId, new FireStoreBridge.OnEventRetrievedListener() {
            @Override
            public void onEventRetrieved(ArrayList<Event> eventList, ArrayList<String> organizerList) {

                if (!eventList.isEmpty()) {
                    // 假设我们只对第一个结果感兴趣
                    Event event = eventList.get(0);
                    textViewEventTitle.setText(event.getTitle());
                    textViewEventDescription.setText(event.getDescription());

                    // 如果事件有海报图像，使用posterCode作为图像ID来显示图像
                    imageDisplay(eventId, imageViewEventPoster);



                    }
                }

        });
    }

    private void joinEvent(String eventId) {
        // Implement the logic to handle the user joining the event.
        // This might involve updating a Firestore collection to add the user to the event.
        //System.out.println("Join event");
        String currentUserId = getCurrentUserId();
        System.out.println(currentUserId);

        fireStoreBridge.updateAttendeeSignUpToEvent(getCurrentUserId(), eventId);
        Toast.makeText(this, "You have joined the event!", Toast.LENGTH_SHORT).show();
    }


    /**
     * A placeholder for an actual method that would retrieve the current user's ID.
     * This needs to be implemented according to how your app manages user authentication.
     *
     * @return the current user's ID as a String.
     */
    private String getCurrentUserId() {
        String userId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        return userId;

    }

    public void imageDisplay(String EventID, ImageView imageView){
        FireStoreBridge fb_events = new FireStoreBridge("EVENT");
        fb_events.displayImage(EventID, imageView);
    }
}

