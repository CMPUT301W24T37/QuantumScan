package com.example.quantumscan;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EventInformationFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView textViewEventTitle;
    private TextView textViewEventDescription;
    private Button buttonJoinEvent;
    private Button buttonReturn;
    private String eventId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_information,container,false);

        textViewEventTitle = view.findViewById(R.id.textViewEventTitle);
        textViewEventDescription = view.findViewById(R.id.textViewEventDescription);
        buttonJoinEvent = view.findViewById(R.id.buttonJoinEvent);
        buttonReturn = view.findViewById(R.id.buttonReturn);

        // Retrieve the event ID passed from AttendeeFragment
        Bundle args = getArguments();
        if (args != null) {
            eventId = args.getString("eventId");
            fetchEventInformation(eventId);
        }

        buttonJoinEvent.setOnClickListener(v -> joinEvent(eventId));
        buttonReturn.setOnClickListener(v -> returnToAttendeeFragment());

        return view;
    }

    private void fetchEventInformation(String eventId) {
        DocumentReference eventRef = db.collection("events").document(eventId);
        eventRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                // Assuming Event class has fields matching Firestore document
                Event event = documentSnapshot.toObject(Event.class);
                if (event != null) {
                    textViewEventTitle.setText(event.getTitle());
                    textViewEventDescription.setText(event.getDescription());
                }
            } else {
                // Handle the case where the event doesn't exist
            }
        }).addOnFailureListener(e -> {
            // Handle any errors
        });
    }

    private void joinEvent(String eventId) {
        // Implement the logic to handle the user joining the event.
        // This might involve updating a Firestore collection to add the user to the event.
    }

    private void returnToAttendeeFragment() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }
}
