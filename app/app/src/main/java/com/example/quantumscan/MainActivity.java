package com.example.quantumscan;

import static android.content.ContentValues.TAG;
import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.example.quantumscan.databinding.ActivityMainBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;  // this is for permission result code
    //private boolean permissionDenied = false;
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        System.out.println(android.os.Build.VERSION.SDK_INT + "  =========================================================================================");
        replaceFragment(new OrganizerFragment());
        DataHolder.getInstance().setEvents();

        Intent intent = new Intent(MainActivity.this, LoginPage.class);
        startActivity(intent);



        String userID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        FireStoreBridge fb = new FireStoreBridge("USER");
        fb.retrieveUser(userID, new FireStoreBridge.OnUserRetrievedListener() {
            @Override
            public void onUserRetrieved(User user, ArrayList<String> attendeeRoles, ArrayList<String> organizerRoles) {
                if (user != null && user.getId() != null && user.getId().trim().equals(userID)) {

                } else {
                    Intent intent = new Intent(MainActivity.this, UserProfileFileActivity.class);
                    startActivity(intent);
                }
            }

        });
        locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        notificationPermissionRequest.launch(permission.POST_NOTIFICATIONS);

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
            }else if(item.getItemId() == R.id.events) {
                replaceFragment(new UserEventFragment());
            }
        });

//        // check if have location permission, if not then ask for permission, otherwise do nothing.
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
//           // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, YOUR_REQUEST_CODE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_REQUEST_CODE);
//                } else {
//                    Log.d("PermissionDemo", "You already have the notification permission!");
//                }
//            }
//        } else {
//            Log.d("atmeng", "you already have the location permission!");
//        }




    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == YOUR_REQUEST_CODE) {
//            // If request is cancelled, the result arrays are empty.
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission was granted, yay! You can now do the notification-related task you need to do.
//            } else {
//                // Permission denied, boo! Disable the functionality that depends on this permission.
//            }
//        }
//    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            // Location permission result handling
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Location permission granted.");
            } else {
                Log.d(TAG, "Location permission denied.");
            }
            // After handling location permission, check and request for notification permission
            requestNotificationPermissionIfNeeded();
        } else if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            // Notification permission result handling
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Notification permission granted.");
            } else {
                Log.d(TAG, "Notification permission denied.");
            }
        }
    }

        private void requestNotificationPermissionIfNeeded() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_REQUEST_CODE);
                }
            }
        }
    private ActivityResultLauncher<String> locationPermissionRequest = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            // Permission is granted. Continue the action or workflow in your app.
        } else {
            // Explain to the user that the feature is unavailable because the feature requires a permission that the user has denied.
        }
        // After location permission handling, request notification permission
        requestNotificationPermissionIfNeeded();
    });
    private ActivityResultLauncher<String> notificationPermissionRequest = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            // Notification permission is granted. You can now enable features that require this permission.
            Log.d(TAG, "Notification permission granted.");
        } else {
            // The user denied the notification permission. Inform them about the limited functionality.
            Log.d(TAG, "Notification permission denied.");
        }
    });
}