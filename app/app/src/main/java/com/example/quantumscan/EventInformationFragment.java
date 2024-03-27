package com.example.quantumscan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

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
        imageViewEventPoster = findViewById(R.id.imageViewEvent);
        buttonJoinEvent = findViewById(R.id.buttonJoinEvent);
        buttonReturn = findViewById(R.id.buttonReturn);


        // Retrieve the event ID passed from AttendeeFragment
        //TODO
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
                    if (event.getPosterCode() != null && !event.getPosterCode().isEmpty()) {
                        fireStoreBridge.displayImage(event.getPosterCode(), imageViewEventPoster);
                    }
                }
            }
        });
    }

    private void joinEvent(String eventId) {
        // Implement the logic to handle the user joining the event.
        // This might involve updating a Firestore collection to add the user to the event.
    }



}

