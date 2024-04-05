package com.example.quantumscan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class OrganizerViewAttendees extends AppCompatActivity {

    ListView eventListView;
    AttendeeContentAdapter attendeeAdapter;
    ArrayList<AttendeeFireBaseHolder> dataList;
    FireStoreBridge fb;
    private ProgressBar mileStoneProgressBar;
    private TextView mileStoneProgressText;
    private TextView mileStoneStatement;
    private int mileStoneValue;
    private ToastManager toast;
    private String eventName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizer_event_attendees_list);
        Button backButton = findViewById(R.id.returnButton);
        TextView titleView = findViewById(R.id.textView_eventName);
        TextView pressableTextView = findViewById(R.id.inviteText);
        eventListView = findViewById(R.id.attendeeList);
        mileStoneProgressBar = findViewById(R.id.milestone_bar);
        mileStoneProgressText = findViewById(R.id.milestoneMessageText);
        mileStoneStatement = findViewById(R.id.milestoneTextMessage);
        toast = ToastManager.getInstance();

        String eventID = getIntent().getStringExtra("eventID");
        eventName = getIntent().getStringExtra("eventName");
        titleView.setText(eventName);
        dataList = new ArrayList<AttendeeFireBaseHolder>();
        fb = new FireStoreBridge("EVENT");
        System.out.println("before view" + eventID);
        fb.retrieveAttendeeCheckIn(eventID, new FireStoreBridge.OnCheckedInListener() {
            @Override
            public void onCheckedInListener(ArrayList<AttendeeFireBaseHolder> attendeeList) {
                if (attendeeList.isEmpty() || attendeeList == null){
                    System.out.println("no attendee joined this event");
                    mileStoneProgressBar.setMax(0);
                    mileStoneProgressBar.setProgress(0);
                    mileStoneProgressText.setText("0");
                    mileStoneStatement.setText("Invite People to your Event!");
                }else {
                    refresh(attendeeList);
                }
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

        int count = checkedInCount(attendeeList);
        mileStoneProgressBar.setMax(dataList.size());
        mileStoneProgressBar.setProgress(count);
        mileStoneProgressText.setText(count + " / " + dataList.size());
        if((int)(count/dataList.size()) == 2){
            mileStoneStatement.setText("Half Way Reached!");
            toast.showToast(this, "You have reached a new Mile Stone for" + eventName );

        }else if(count == dataList.size()){
            mileStoneStatement.setText("Next: Half Way Mile Stone...");


        }else if ((int)(count/dataList.size()) > 2){
            mileStoneStatement.setText("Next: Final Mile Stone...");


        }else{
            mileStoneStatement.setText("Final Mile Stone Reached!");
            toast.showToast(this, "You have reached a new Mile Stone for "+eventName);

        }
    }

    private int checkedInCount(ArrayList<AttendeeFireBaseHolder> attendeeList){
        int count = 0;
        for(int i = 0; i < attendeeList.size(); i++){
            if(attendeeList.get(i).getCheckInCount() >= 1){
                count++;
            }
        }
        return count;
    }

}
