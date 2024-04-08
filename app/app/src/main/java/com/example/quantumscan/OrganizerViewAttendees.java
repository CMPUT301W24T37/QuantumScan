package com.example.quantumscan;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;

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
    private Context context;
    private static final String CHANNEL_ID = "notification_new";
    private boolean isFirstLoad = false;

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
        context = this;
        createNotificationChannel();
        String eventID = getIntent().getStringExtra("eventID");
        eventName = getIntent().getStringExtra("eventName");
        titleView.setText(eventName);
        dataList = new ArrayList<AttendeeFireBaseHolder>();
        fb = new FireStoreBridge("EVENT");

        fb.retrieveAttendeeCheckIn(eventID, new FireStoreBridge.OnCheckedInListener() {
            @Override
            public void onCheckedInListener(ArrayList<AttendeeFireBaseHolder> attendeeList) {
                if (attendeeList.isEmpty() || attendeeList == null) {
                    mileStoneProgressBar.setMax(0);
                    mileStoneProgressBar.setProgress(0);
                    mileStoneProgressText.setText("0");
                    mileStoneStatement.setText("No MileStone");
                } else {
                    refresh(attendeeList, eventID);
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

    private void refresh(ArrayList<AttendeeFireBaseHolder> attendeeList, String eventID) {
        dataList = attendeeList;
        attendeeAdapter = new AttendeeContentAdapter(this, dataList);
        eventListView.setAdapter(attendeeAdapter);
        int checkInCount = checkedInCountProgress(attendeeList);
        int totalCount = attendeeList.size();
        mileStoneProgressBar.setMax(dataList.size());
        mileStoneProgressBar.setProgress(checkInCount);
        mileStoneProgressText.setText(checkInCount + " / " + attendeeList.size());
        fb.retrieveAndCompareMileStone(eventID, new FireStoreBridge.OnRetrieveMileStone() {
            @Override
            public void onRetrieveMileStone(Date halfWay, Date finalWay) {
                if (finalWay == null && checkInCount == totalCount) {
                    if (!isFirstLoad) {
                        showNotification("Event: All User Checked In", "you have reached a new mile stone");
                    }
                    System.out.println("new mile stone");
                    Timestamp currentTimestamp = Timestamp.now();
                    fb.updateMileStone(eventID, currentTimestamp, currentTimestamp);

                } else if(finalWay != null && checkInCount == totalCount){
                    if (!isFirstLoad) {
                        showNotification("Since Event Start: ", "All New Joined User Have Checked IN");
                    }
                }else if (finalWay == null && checkInCount != totalCount) {

                }
            }
        });

    }

    private int checkedInCount(ArrayList<AttendeeFireBaseHolder> attendeeList) {
        int count = 0;
        int countRepeat = 0;
        for (int i = 0; i < attendeeList.size(); i++) {
            if (attendeeList.get(i).getCheckInCount() >= 1) {
                count++;
            }
            if(attendeeList.get(i).getCheckInCount() > 1){
                countRepeat++;
            }
        }
        return count-countRepeat;
    }
    private int checkedInCountProgress(ArrayList<AttendeeFireBaseHolder> attendeeList) {
        int count = 0;
        int countRepeat = 0;
        for (int i = 0; i < attendeeList.size(); i++) {
            if (attendeeList.get(i).getCheckInCount() >= 1) {
                count++;
            }

        }
        return count;
    }

    private void createNotificationChannel() {
        // Check if the Android version is greater than or equal to Android 8 (Oreo)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "notification new"; // Channel name for the user
            String description = "notification new"; // Channel description for the user
            int importance = NotificationManager.IMPORTANCE_HIGH; // Set the importance level
            NotificationChannel channel = new NotificationChannel("notification new", name, importance);
            channel.setDescription(description);
            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }
    }

    private void showNotification(String message1, String message2) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notification new")
                .setSmallIcon(R.drawable.ic_message) // Set the icon
                .setContentTitle(message1) // Set the title of the notification
                .setContentText(message2) // Set the text
                .setPriority(NotificationCompat.PRIORITY_HIGH); // Set the priority

        // Show the notification
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        notificationManager.notify(1, builder.build()); // The first parameter is a unique ID for the notification
    }
    @Override
    public void onResume() {
        super.onResume();
        // Consider if you need to reset isFirstLoad here based on your app's logic
        // isFirstLoad = true;
        System.out.println("resum,e");
        isFirstLoad = true;
    }

    @Override
    public void onStop() {

        super.onStop();
        System.out.println("stopped");
        isFirstLoad = false;
    }

}
