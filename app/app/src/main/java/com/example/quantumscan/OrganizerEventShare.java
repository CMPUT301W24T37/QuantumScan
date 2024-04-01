package com.example.quantumscan;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrganizerEventShare extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizer_event_share);
        Button backButton = findViewById(R.id.returnButton);
        ImageView QRcodeView = findViewById(R.id.imageViewQRcode);

        String eventID = getIntent().getStringExtra("eventID");

        Bitmap bitmapQRcode = QRCodeGenerator.generateQRCodeBitmap(eventID,250,250);
        QRcodeView.setImageBitmap(bitmapQRcode);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
