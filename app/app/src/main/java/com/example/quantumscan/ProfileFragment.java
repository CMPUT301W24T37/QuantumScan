package com.example.quantumscan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Random;
import android.widget.EditText;

public class ProfileFragment extends Fragment {
    ImageView imageView;
    EditText userName;
    EditText userUniversity;
    EditText userPhoneNumb;
    EditText userEmail;
    EditText userInfo;
    Button editPhoto;
    Button deletePhoto;


    String name;
    String university;
    String phoneNumb;
    String email;
    String info;
    String imageUrl;
    private SelectImage selectImage;
    private GestureDetector gestureDetector;

    private Uri imageUri = null;

    private final ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    String userId = Settings.Secure.getString(this.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                    System.out.println(userId);
                    photoUpdate(userId, imageUri);
                    editPhoto.setVisibility(View.GONE);
                    deletePhoto.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getContext(), "Please select an image", Toast.LENGTH_SHORT).show();
                    editPhoto.setVisibility(View.GONE);
                    deletePhoto.setVisibility(View.GONE);
                }
            });
    String pictureName;
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

        FrameLayout profilePictureContainer = view.findViewById(R.id.profilePictureContainer);

        editPhoto = view.findViewById(R.id.editProfilePhoto);

        deletePhoto = view.findViewById(R.id.deleteProfilePhoto);

        String userId = Settings.Secure.getString(this.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        FireStoreBridge fb1 = new FireStoreBridge("USER");

        selectImage = new SelectImage(getActivity(), activityResultLauncher);
        editPhoto.setOnClickListener(v -> {
            selectImage.pickImage();
            editPhoto.setVisibility(View.GONE);
            deletePhoto.setVisibility(View.GONE);
        });

        profilePictureContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPhoto.setVisibility(View.VISIBLE);
                deletePhoto.setVisibility(View.VISIBLE);
            }
        });

        deletePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteToPhoto = userName.getText().toString();
                pictureName = randomPickForDelete(deleteToPhoto);
                fb1.deleteProfilePhoto(userId, pictureName);
                fb1.displayProfile(pictureName, imageView);
                editPhoto.setVisibility(View.GONE);
                deletePhoto.setVisibility(View.GONE);
            }
        });


        fb1.retrieveUser(userId, new FireStoreBridge.OnUserRetrievedListener() {
            @Override
            public void onUserRetrieved(User user, ArrayList<String> attendeeRoles, ArrayList<String> organizerRoles) {

                imageUrl = user.getProfilePicture();
                if (imageUrl.equals("DEFAULT_PFP")){
                    pictureName = randomPick(user);
                    fb1.updateProfilePhoto(user.getId(), pictureName);
                    fb1.displayProfile(pictureName, imageView);
                }else{
                    fb1.displayProfile(imageUrl, imageView);
                }

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
                userName.setEnabled(true);
                userUniversity.setEnabled(true);
                userPhoneNumb.setEnabled(true);
                userEmail.setEnabled(true);
                userInfo.setEnabled(true);
                userName.requestFocus();
                userName.setSelection(userName.getText().length());
                //showUserInfoDialog();
            }
        });

        gestureDetector = new GestureDetector(this.getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                System.out.println("double tab dectedeed 1");

                return true;
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Code to make EditText non-editable
                userName.setEnabled(false);
                userUniversity.setEnabled(false);
                userPhoneNumb.setEnabled(false);
                userEmail.setEnabled(false);
                userInfo.setEnabled(false);

                if (userName.getText().toString().trim().length() == 0) {
                    Toast.makeText(getContext(), "User name can not be empty. Change cancelled", Toast.LENGTH_LONG).show();
                }
                else {
                    // edit user info here
                    name = userName.getText().toString();
                    phoneNumb = userPhoneNumb.getText().toString();
                    email = userEmail.getText().toString();
                    info = userUniversity.getText().toString();
                    UserFireBaseHolder user = new UserFireBaseHolder(name, phoneNumb, info, "DEFAULT_PFP", email);
                    String userID = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                    user.setId(userID);
                    fb.updateUser(user);
                }
                return gestureDetector.onTouchEvent(event);
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
                UserFireBaseHolder user = new UserFireBaseHolder(name, phoneNumb, info, "DEFAULT_PFP", email);
                String userID = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                user.setId(userID);
                fb.updateUser(user);
            }

            dialog.dismiss(); // Close the dialog
        });

        dialog.show();
    }

    public String randomPick(User user){
        String Name = user.getName().toString();
        char firstLetter = Name.charAt(0);
        if (!(firstLetter >= 'A' && firstLetter <= 'Z') && !(firstLetter >= 'a' && firstLetter <= 'z')) {
            firstLetter = '?';
        }
        Random rand = new Random();
        int rand_int1 = rand.nextInt(4)+1;
        String pictureName = "" + firstLetter + rand_int1;
        pictureName = pictureName.toUpperCase()+".png";
        return pictureName;
    }

    public String randomPickForDelete(String userName){
        char firstLetter = userName.charAt(0);
        if (!(firstLetter >= 'A' && firstLetter <= 'Z') && !(firstLetter >= 'a' && firstLetter <= 'z')) {
            firstLetter = '?';
        }
        Random rand = new Random();
        int rand_int1 = rand.nextInt(4)+1;
        String pictureName = "" + firstLetter + rand_int1;
        pictureName = pictureName.toUpperCase()+".png";
        return pictureName;
    }


    public void photoUpdate(String userID, Uri imageUri){
        FireStoreBridge fb_user = new FireStoreBridge("USER");
        fb_user.updatePhoto(userID, imageUri);

    }
}