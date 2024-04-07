package com.example.quantumscan;

import android.app.Dialog;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdminProfileDetail extends AppCompatActivity {

    TextView userName;
    TextView userUniversity;
    TextView userPhoneNumb;
    TextView userEmail;
    TextView userInfo;
    Button delete;
    Button deletePfp;

    String name;
    String university;
    String phoneNumb;
    String email;
    String info;
    Admin admin;
    private FireStoreBridge fb = new FireStoreBridge("USER");

    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_profile_details);
        userName = findViewById(R.id.userNameText);
        userUniversity = findViewById(R.id.userUniversityText);
        userPhoneNumb = findViewById(R.id.userPhoneNumbText);
        userEmail = findViewById(R.id.userEmailText);
        userInfo = findViewById(R.id.userInfoText);
        delete = findViewById(R.id.buttonDelete);
        deletePfp = findViewById(R.id.buttonDeletePfp);
        admin = new Admin();

        String id = getIntent().getStringExtra("userID");
        String name = getIntent().getStringExtra("userName");

        FireStoreBridge fb1 = new FireStoreBridge("USER");
        Button backButton = findViewById(R.id.returnButton);
        fb1.retrieveUser(id, new FireStoreBridge.OnUserRetrievedListener() {
            @Override
            public void onUserRetrieved(User user, ArrayList<String> attendeeRoles, ArrayList<String> organizerRoles) {
                userName.setText(user.getName());
                userUniversity.setText(user.getUniversity());
                userPhoneNumb.setText(user.getPhone());
                userEmail.setText(user.getEmail());
                userInfo.setText(user.getId());

            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                admin.deleteUserProfile(id);
            }
        });

        deletePfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                admin.deleteUserAvatar(id);
                StorageReference desertRef = storage.getReference().child("default_avatars/" + id + ".jpg");
                desertRef.delete();
            }
        });
    }


}
