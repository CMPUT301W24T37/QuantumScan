package com.example.quantumscan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class OrganizerViewAttendees extends AppCompatActivity {

    ListView eventListView;
    AttendeeContentAdapter attendeeAdapter;
    ArrayList<AttendeeFireBaseHolder> dataList;
    FireStoreBridge fb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizer_event_attendees_list);
        Button backButton = findViewById(R.id.returnButton);
        TextView titleView = findViewById(R.id.textView_eventName);
        TextView pressableTextView = findViewById(R.id.inviteText);
        eventListView = findViewById(R.id.attendeeList);

        String eventID = getIntent().getStringExtra("eventID");
        String eventName = getIntent().getStringExtra("eventName");
        titleView.setText(eventName);
        dataList = new ArrayList<AttendeeFireBaseHolder>();
        fb = new FireStoreBridge("EVENT");
        System.out.println("before view" + eventID);
        fb.retrieveAttendeeCheckIn(eventID, new FireStoreBridge.OnCheckedInListener() {
            @Override
            public void onCheckedInListener(ArrayList<AttendeeFireBaseHolder> attendeeList) {
                if (attendeeList.isEmpty() || attendeeList == null){
                    System.out.println("no attendee joined this event");
                }
                refresh(attendeeList);
            }

        });

        //String []attendees ={"Austin", "ZhiYang", "Wei","David","Karl","Kaining"};


//        dataList = new ArrayList<AttendeeFireBaseHolder>();
//        //dataList.addAll(Arrays.asList(attendees));
//        attendeeAdapter = new AttendeeContentAdapter(this, dataList);
//        eventListView.setAdapter(attendeeAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pressableTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(OrganizerViewAttendees.this, OrganizerEventShare.class);
                detailIntent.putExtra("eventID", eventID);
                startActivity(detailIntent);

            }
        });

    }

    private void refresh(ArrayList<AttendeeFireBaseHolder> attendeeList){
        dataList = attendeeList;
        attendeeAdapter = new AttendeeContentAdapter(this, dataList);
        eventListView.setAdapter(attendeeAdapter);
    }

}
