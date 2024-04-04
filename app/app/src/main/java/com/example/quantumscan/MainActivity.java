package com.example.quantumscan;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.example.quantumscan.databinding.ActivityMainBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new OrganizerFragment());
        DataHolder.getInstance().setEvents();

        Intent intent = new Intent(MainActivity.this, LoginPage.class);
        startActivity(intent);

        // authentication start
//        String userID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//        FireStoreBridge fb = new FireStoreBridge("USER");
//        fb.retrieveUser(userID, new FireStoreBridge.OnUserRetrievedListener() {
//            @Override
//            public void onUserRetrieved(User user, ArrayList<String> attendeeRoles, ArrayList<String> organizerRoles) {
//                if (user != null && user.getId() != null && user.getId().trim().equals(userID)) {
//
//                } else {
//                    Intent intent = new Intent(MainActivity.this, UserProfileFileActivity.class);
//                    startActivity(intent);
//                }
//            }
//
//        });
        // authentication end

        binding.bottomNavigationView.setOnItemReselectedListener(item -> {
            if(item.getItemId() == R.id.organizer){
                replaceFragment(new OrganizerFragment());
            }else if(item.getItemId() == R.id.attendee){
                replaceFragment(new AttendeeFragment());
            }else if(item.getItemId() == R.id.community){
                replaceFragment(new CommunityFragment());
            }else if(item.getItemId() == R.id.profile){
                replaceFragment(new ProfileFragment());
            }
        });
    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }


}