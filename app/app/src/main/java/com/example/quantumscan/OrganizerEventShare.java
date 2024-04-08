package com.example.quantumscan;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;

public class OrganizerEventShare extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizer_event_share);
        Button backButton = findViewById(R.id.returnButton);
        ImageView QRcodeView = findViewById(R.id.imageViewQRcode);
        Button shareButton = findViewById(R.id.shareButton);

        String eventID = getIntent().getStringExtra("eventID");

        Bitmap bitmapQRcode = QRCodeGenerator.generateQRCodeBitmap(eventID,250,250);
        QRcodeView.setImageBitmap(bitmapQRcode);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (ActivityCompat.checkSelfPermission(OrganizerEventShare.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(OrganizerEventShare.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                }
                Log.d("atmeng", "you already have the write external permission!");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmapQRcode.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(OrganizerEventShare.this.getContentResolver(), bitmapQRcode, "Title", "descr");
                Uri image = Uri.parse(path);
                Intent intent = new Intent(Intent.ACTION_SEND, image);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Scan this QR code to signed up or check in the event");
                intent.putExtra(Intent.EXTRA_STREAM, image);
                intent.putExtra(Intent.EXTRA_EMAIL, "");

                // Create intent to show chooser
                Intent chooser = Intent.createChooser(intent, "share QR code image");


                // Try to invoke the intent.
                try {
                    startActivity(chooser);
                } catch (ActivityNotFoundException e) {
                    // Define what your app should do if no activity can handle the intent.
                }


            }
        });

    }
}
