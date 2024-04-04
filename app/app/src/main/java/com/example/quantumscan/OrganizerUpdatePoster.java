package com.example.quantumscan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class OrganizerUpdatePoster extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_STORAGE = 2;

    private ImageView imageView;
    private SelectImage selectImage;

    private String eventID;
    private String eventName;
    private Uri imageUri = null;
    private final ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    this.imageUpdate(eventID, imageView, imageUri);

                } else {
                    Toast.makeText(OrganizerUpdatePoster.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizer_event_update_poster);

        imageView = findViewById(R.id.image_view);
        Button btnSelectImage = findViewById(R.id.button_choose_image);
        Button backButton = findViewById(R.id.returnButton);

        eventID = getIntent().getStringExtra("eventID");
        eventName = getIntent().getStringExtra("eventName");
        //System.out.println("EventID"+eventID);
        //System.out.println("EventName"+eventName);
        //this.imageDisplay(eventID, imageView);
        selectImage = new SelectImage(this, activityResultLauncher);
        btnSelectImage.setOnClickListener(v -> selectImage.pickImage());

        //this.imageDisplay(eventID, imageView);
        /*
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(OrganizerUpdatePoster.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(OrganizerUpdatePoster.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
                } else {
                    openGallery();
                }
            }
        });
        */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void imageDisplay(String EventID, ImageView imageView){
        FireStoreBridge fb_events = new FireStoreBridge("EVENT");
        fb_events.displayImage(EventID, imageView);
    }
    public void imageUpdate(String EventID, ImageView imageView, Uri imageUri){
        FireStoreBridge fb_events = new FireStoreBridge("EVENT");
        fb_events.updateImage(EventID, imageView, imageUri);
    }
    /*
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

     */

}

