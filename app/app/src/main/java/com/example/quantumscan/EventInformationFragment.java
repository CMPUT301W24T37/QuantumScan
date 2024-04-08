package com.example.quantumscan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;


import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_event_information);

        textViewEventTitle = findViewById(R.id.textViewEventTitle);
        textViewEventDescription = findViewById(R.id.textViewEventDescription);

        buttonJoinEvent = findViewById(R.id.buttonJoinEvent);
        buttonReturn = findViewById(R.id.buttonReturn);
        imageViewEventPoster = findViewById(R.id.imageViewEvent);
        context = this;


        // Retrieve the event ID passed from AttendeeFragment

        eventId = getIntent().getStringExtra("eventID");


        buttonJoinEvent.setOnClickListener(v -> joinEvent(eventId));
        fireStoreBridge = new FireStoreBridge("EVENT");

        //
        fetchEventInformation(eventId);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //
            }
        });
    }

    private void fetchEventInformation(String eventId) {
        //
        fireStoreBridge.retrieveEvent(eventId, new FireStoreBridge.OnEventRetrievedListener() {
            @Override
            public void onEventRetrieved(ArrayList<Event> eventList, ArrayList<String> organizerList) {

                if (!eventList.isEmpty()) {
                    //
                    Event event = eventList.get(0);
                    textViewEventTitle.setText(event.getTitle());
                    textViewEventDescription.setText(event.getDescription());
                    //
                    imageDisplay(eventId, imageViewEventPoster);

                }
            }

        });
    }

    private void joinEvent(String eventId) {

        String currentUserId = getCurrentUserId();
        fireStoreBridge.retrieveUser(currentUserId, new FireStoreBridge.OnUserRetrievedListener() {
            @Override
            public void onUserRetrieved(User user, ArrayList<String> attendeeRoles, ArrayList<String> organizerRoles) {
                String name = user.getName();
                String email = user.getEmail();
                String phone = user.getPhone();
                String university = user.getUniversity();

                if (name.equals("UserName") && phone.equals("*0000000000") && email.equals("email@example.com") && university.equals("Default University")) {
                    showUserInputDialogue() ;

                } else {
                    fireStoreBridge.updateAttendeeSignUpToEvent(getCurrentUserId(), eventId, context);

                }

            }
        });

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

    public void imageDisplay(String EventID, ImageView imageView) {
        FireStoreBridge fb_events = new FireStoreBridge("EVENT");
        fb_events.displayImage(EventID, imageView);
    }

    private void showUserInputDialogue() {
        // Context for creating the views
        final Context context = this;

        // Create a LinearLayout container
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create EditText fields
        final EditText usernameEditText = new EditText(context);
        usernameEditText.setHint("Username");

        final EditText emailEditText = new EditText(context);
        emailEditText.setHint("Email");

        final EditText phoneEditText = new EditText(context);
        phoneEditText.setHint("Phone");

        final EditText universityEditText = new EditText(context);
        universityEditText.setHint("University");

        // Add EditText fields to the LinearLayout
        layout.addView(usernameEditText);
        layout.addView(emailEditText);
        layout.addView(phoneEditText);
        layout.addView(universityEditText);

        // Ensure the EditTexts are properly spaced and sized
        for (int i = 0; i < layout.getChildCount(); i++) {
            View v = layout.getChildAt(i);
            if (v instanceof EditText) {
                EditText editText = (EditText) v;
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.setMinLines(1);
                editText.setMaxLines(1);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(50, 20, 50, 20); // Adjust margins as needed
                editText.setLayoutParams(params);
            }
        }

        // Create the AlertDialog and set the LinearLayout as its view
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(layout);

        // Configure the dialog buttons
        dialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Here, you would handle the input from the EditText fields
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String university = universityEditText.getText().toString().trim();
                //fireStoreBridge.updateAttendeeSignUpToEvent(getCurrentUserId(), eventId);
                UserFireBaseHolder user = new UserFireBaseHolder();
                user.setName(username);
                user.setEmail(email);
                user.setPhone(phone);
                user.setUniversity(university);
                user.setId(getCurrentUserId());
                fireStoreBridge.updateUser(user);

            }
        });

        dialogBuilder.setNegativeButton("Cancel", null);

        // Show the dialog
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}