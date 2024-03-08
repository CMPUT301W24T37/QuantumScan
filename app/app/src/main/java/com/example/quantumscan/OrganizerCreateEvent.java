package com.example.quantumscan;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class OrganizerCreateEvent extends AppCompatActivity {

    private SelectImage selectImage;
    private Uri imageUri = null;
    private FireStoreBridge fb  = new FireStoreBridge("EVENT");;

    // Create an ActivityResultLauncher instance directly within the Activity

    public interface imageUrlUploadListener{
        void updateEventImage(String eventId, String imageURL);
    }

    // REFERENCE CODE LINK: https://github.com/Everyday-Programmer/Upload-Image-to-Firebase-Android/blob/main/app/src/main/java/com/example/uploadimagefirebase/MainActivity.java
    private final ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    System.out.println(imageUri);
                    // Handle the imageUri, e.g., display it or prepare it for upload

                        imageUrlUploadListener listener = null;
                        listener.updateEventImage("asdf", imageUri.toString());
                } else {
                    Toast.makeText(OrganizerCreateEvent.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizer_create);

        // Components from this page
        Button buttonReturn = (Button)findViewById(R.id.returnButton);
        Button buttonSave = (Button)findViewById(R.id.saveButton);
        Button buttonPickImage = (Button) findViewById(R.id.picButton);
        EditText editTextName = (EditText) findViewById(R.id.nameEditText);
        EditText editTextInfo = (EditText) findViewById(R.id.infoEditText);
        EditText editTextID = (EditText) findViewById(R.id.idEditText);

        String nameText;
        String infoText;
        //String idText;

        selectImage = new SelectImage(this, activityResultLauncher);
        buttonPickImage.setOnClickListener(v -> selectImage.pickImage());
        //System.out.println(imageUri);




        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String nameText = editTextName.getText().toString();
                String infoText = editTextInfo.getText().toString();
                Event newEvent = new Event();
                newEvent.EventIdGenerator("uyiuyui"); //TODO: properly get user id
                newEvent.setDescription(infoText);
                newEvent.setTitle(nameText);
                fb.updateEvent(newEvent,"uyiuyui"); //TODO: you just need to pass in newEvent and userid as parameter
                finish();

            }


        });


    }


}
