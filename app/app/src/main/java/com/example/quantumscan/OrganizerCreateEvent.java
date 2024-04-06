package com.example.quantumscan;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class OrganizerCreateEvent extends AppCompatActivity {

    private SelectImage selectImage;
    private Uri imageUri = null;
    private String userID;
    private int eventStartTime;
    private FireStoreBridge fb  = new FireStoreBridge("EVENT");
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
        String nameText;
        String infoText;

        //String idText;

        selectImage = new SelectImage(this, activityResultLauncher);
        buttonPickImage.setOnClickListener(v -> selectImage.pickImage());

        Spinner numberSpinner = findViewById(R.id.number_spinner);

        // Create a list of numbers from 0 to 31
        List<String> numbers = new ArrayList<>();
        for (int i = 0; i <= 31; i++) {
            numbers.add(String.valueOf(i));
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numbers);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        numberSpinner.setAdapter(adapter);


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



                fb.updateEvent(newEvent,userID);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("eventID", newEvent.getId());
                returnIntent.putExtra("eventName", newEvent.getTitle());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }

        });

        numberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Your code here. For example:

                eventStartTime = Integer.parseInt(numbers.get(position));

                Toast.makeText(OrganizerCreateEvent.this, "Selected: " + numbers.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Your code here, if needed
                eventStartTime = 0;
            }
        });


    }


}
