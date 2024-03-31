package com.example.quantumscan;

import android.app.Dialog;
import android.os.Bundle;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    ImageView imageView;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        userName = view.findViewById(R.id.userNameText);
        userUniversity = view.findViewById(R.id.userUniversityText);
        userPhoneNumb = view.findViewById(R.id.userPhoneNumbText);
        userEmail = view.findViewById(R.id.userEmailText);
        userInfo = view.findViewById(R.id.userInfoText);
        imageView = view.findViewById(R.id.profileImage);
        String userId = Settings.Secure.getString(this.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        FireStoreBridge fb1 = new FireStoreBridge("USER");
        fb1.retrieveUser(userId, new FireStoreBridge.OnUserRetrievedListener() {
            @Override
            public void onUserRetrieved(User user, ArrayList<String> attendeeRoles, ArrayList<String> organizerRoles) {
                userName.setText(user.getName());
                userUniversity.setText(user.getUniversity());
                userPhoneNumb.setText(user.getPhone());
                userEmail.setText(user.getEmail());
                userInfo.setText(user.getId());
            }
        });


        Button showInfoDialogButton = view.findViewById(R.id.showInfoDialogButton);
        showInfoDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserInfoDialog();
            }
        });



        return view;
    }

    public void showUserInfoDialog() {
        final Dialog dialog = new Dialog(getActivity());
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
                Toast.makeText(getContext(), "User name can not be empty. Change cancelled", Toast.LENGTH_LONG).show();
            }
            else {
                // edit user info here
                name = userName.getText().toString();
                phoneNumb = userPhoneNumber.getText().toString();
                email = userEmail.getText().toString();
                info = userUniversity.getText().toString();
                UserFireBaseHolder user = new UserFireBaseHolder(name, phoneNumb, info, "profilePic", email);
                String userID = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                user.setId(userID);
                fb.updateUser(user);
            }

            dialog.dismiss(); // Close the dialog
        });

        dialog.show();
    }
}