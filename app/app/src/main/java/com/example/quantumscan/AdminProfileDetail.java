package com.example.quantumscan;

import android.app.Dialog;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdminProfileDetail extends AppCompatActivity {

    TextView userName;
    TextView userUniversity;
    TextView userPhoneNumb;
    TextView userEmail;
    TextView userInfo;

    String name;
    String university;
    String phoneNumb;
    String email;
    String info;
    private FireStoreBridge fb = new FireStoreBridge("USER");

    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_details);
        userName = findViewById(R.id.userNameText);
        userUniversity = findViewById(R.id.userUniversityText);
        userPhoneNumb = findViewById(R.id.userPhoneNumbText);
        userEmail = findViewById(R.id.userEmailText);
        userInfo = findViewById(R.id.userInfoText);

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

        Button showInfoDialogButton = findViewById(R.id.showInfoDialogButton);
        showInfoDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserInfoDialog();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void showUserInfoDialog() {
        final Dialog dialog = new Dialog(AdminProfileDetail.this);
        dialog.setContentView(R.layout.dialog_user_info);
        dialog.setTitle("User Information");

        // Find views
        EditText userName = dialog.findViewById(R.id.userName);
        EditText userPhoneNumber = dialog.findViewById(R.id.userPhoneNumber);
        EditText userEmail = dialog.findViewById(R.id.userEmail);
        EditText userUniversity = dialog.findViewById(R.id.userUniversity);
        Button submitButton = dialog.findViewById(R.id.buttonSubmit);

        submitButton.setOnClickListener(v -> {
            if (userName.getText().toString().trim().length() == 0) {
                Toast.makeText(AdminProfileDetail.this, "User name can not be empty. Change cancelled", Toast.LENGTH_LONG).show();
            }
            else {
                // edit user info here
                name = userName.getText().toString();
                phoneNumb = userPhoneNumber.getText().toString();
                email = userEmail.getText().toString();
                info = userUniversity.getText().toString();
                UserFireBaseHolder user = new UserFireBaseHolder(name, phoneNumb, info, "profilePic", email);
                String userID = Settings.Secure.getString(AdminProfileDetail.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                user.setId(userID);
                fb.updateUser(user);
            }

            dialog.dismiss(); // Close the dialog
        });

        dialog.show();
    }
}
