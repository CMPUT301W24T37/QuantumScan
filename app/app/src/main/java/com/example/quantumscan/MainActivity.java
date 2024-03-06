package com.example.quantumscan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

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
        FireStoreBridge fb = new FireStoreBridge("USER");
        fb.retrieveUser("1658f5315ca1a74d", new FireStoreBridge.OnUserRetrievedListener() {
            @Override
            public void onUserRetrieved(User user, ArrayList<String> attendeeRoles, ArrayList<String> organizerRoles) {
                System.out.println(user.getName());
            }
        });

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