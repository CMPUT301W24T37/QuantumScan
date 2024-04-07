package com.example.quantumscan;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.UploadTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrganizerCreateEvent extends AppCompatActivity {

    private SelectImage selectImage;
    private Uri imageUri = null;
    private String userID;
    private int eventStartTime;
    private TextView startTimeText, endTimeText;
    private FireStoreBridge fb  = new FireStoreBridge("EVENT");

    private Context context;
    // FirebaseStorage storage = FirebaseStorage.getInstance();

    // Create an ActivityResultLauncher instance directly within the Activity

    public interface imageUrlUploadListener{
        void updateEventImage(String eventId, String imageURL);
        void uploadEventImage(Event newEvent ,String evenID, Uri imageURI);
    }

    // REFERENCE CODE LINK: https://github.com/Everyday-Programmer/Upload-Image-to-Firebase-Android/blob/main/app/src/main/java/com/example/uploadimagefirebase/MainActivity.java
    private final ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    System.out.println(imageUri);

                } else {
                    Toast.makeText(OrganizerCreateEvent.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizer_create);
        userID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        // Components from this page
        Button buttonReturn = (Button)findViewById(R.id.returnButton);
        Button buttonSave = (Button)findViewById(R.id.saveButton);
        Button buttonPickImage = (Button) findViewById(R.id.picButton);
        EditText editTextName = (EditText) findViewById(R.id.nameEditText);
        EditText editTextInfo = (EditText) findViewById(R.id.infoEditText);
        EditText editTextLimit = (EditText) findViewById(R.id.idEditText);
        startTimeText = findViewById(R.id.startTime);
        endTimeText = findViewById(R.id.endTime);
        context = this;
        String nameText;
        String infoText;

        //String idText;

        selectImage = new SelectImage(this, activityResultLauncher);
        buttonPickImage.setOnClickListener(v -> selectImage.pickImage());



        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                System.out.println("imageUri after pick " + imageUri);

                String nameText = editTextName.getText().toString();
                String infoText = editTextInfo.getText().toString();
                Event newEvent = new Event();
                newEvent.EventIdGenerator(userID);
                newEvent.setDescription(infoText);
                newEvent.setTitle(nameText);
                if (editTextLimit.getText().toString().trim().matches("")) {
                    newEvent.setAttendeeLimit(Long.parseLong("9999999"));
                } else {
                    newEvent.setAttendeeLimit(Long.parseLong(editTextLimit.getText().toString().trim()));
                }
                newEvent.setCurrentTotalAttendee(0);


                String EventID = newEvent.getId();

                fb.uploadEventImage(newEvent, EventID, imageUri);
                fb.updateEvent(newEvent,userID, startTimeText.getText().toString().trim(), endTimeText.getText().toString().trim() );

                Intent returnIntent = new Intent();
                returnIntent.putExtra("eventID", newEvent.getId());
                returnIntent.putExtra("eventName", newEvent.getTitle());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }

        });
        startTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDateTimeDialog(startTimeText);

            }
        });
        endTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startTimeText.getText().toString().isEmpty()){
                    Toast.makeText(context, "please select a start time first!", Toast.LENGTH_SHORT).show();
                }else{
                    showEndDateTimeDialog(endTimeText);
                }
            }
        });

//        numberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                // Your code here. For example:
//
//                eventStartTime = Integer.parseInt(numbers.get(position));
//
//                Toast.makeText(OrganizerCreateEvent.this, "Selected: " + numbers.get(position), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // Your code here, if needed
//                eventStartTime = 0;
//            }
//        });


    }

    private void showEndDateTimeDialog(final TextView date_time_in) {
        // Inflate the custom layout
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.date_time_dialog, null);
        final DatePicker datePicker = dialogView.findViewById(R.id.date_picker);
        final TimePicker timePicker = dialogView.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true); // Use 24 hour view or not based on your preference

        // Create the dialog
        final Dialog dateTimeDialog = new Dialog(this);
        dateTimeDialog.setContentView(dialogView);
        dateTimeDialog.setTitle("Select Date and Time");

        // Set up the OK button
        Button btnOk = new Button(this);
        btnOk.setText("OK");
        btnOk.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                    timePicker.getCurrentHour(), timePicker.getCurrentMinute());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            try {
                Date dateTime1 = format.parse(startTimeText.getText().toString().trim());
                Date dateTime2 = format.parse(format.format(calendar.getTime()));
                if (dateTime2.before(dateTime1)) {
                    Toast.makeText(context, "please select a time later than start time", Toast.LENGTH_SHORT).show();
                    showEndDateTimeDialog(endTimeText);

                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            date_time_in.setText(format.format(calendar.getTime()));

            dateTimeDialog.dismiss();
        });

        // Add the OK button to the dialog
        ((ViewGroup) dialogView).addView(btnOk);

        // Show the custom dialog
        dateTimeDialog.show();
    }

    private void showStartDateTimeDialog(final TextView date_time_in) {
        // Inflate the custom layout
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.date_time_dialog, null);
        final DatePicker datePicker = dialogView.findViewById(R.id.date_picker);
        final TimePicker timePicker = dialogView.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true); // Use 24 hour view or not based on your preference

        // Create the dialog
        final Dialog dateTimeDialog = new Dialog(this);
        dateTimeDialog.setContentView(dialogView);
        dateTimeDialog.setTitle("Select Date and Time");

        // Set up the OK button
        Button btnOk = new Button(this);
        btnOk.setText("OK");
        btnOk.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                    timePicker.getCurrentHour(), timePicker.getCurrentMinute());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

            date_time_in.setText(format.format(calendar.getTime()));

            dateTimeDialog.dismiss();
        });
        // Add the OK button to the dialog
        ((ViewGroup) dialogView).addView(btnOk);

        // Show the custom dialog
        dateTimeDialog.show();
    }

}
