package com.example.quantumscan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class OrganizerEventFragment extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_organizer_event_homepage);

        TextView cityNameView = findViewById(R.id.textView_eventName);
        Button backButton = findViewById(R.id.returnButton);

        // Retrieve the city name passed from MainActivity
        String cityName = getIntent().getStringExtra("eventName");
        cityNameView.setText(cityName);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to MainActivity
                finish();
            }
        });
    }

}
