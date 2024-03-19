package com.example.quantumscan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.quantumscan.databinding.ActivityUserProfileFileBinding;

import org.jetbrains.annotations.Contract;

public class UserProfileFileActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityUserProfileFileBinding binding;
    private EditText userNameText, emailText, phoneText, universityText;
    private Button confirmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserProfileFileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        confirmButton = findViewById(R.id.confirm_button);
        userNameText = findViewById(R.id.username_edit_text);
        emailText = findViewById(R.id.email_edit_text);
        phoneText = findViewById(R.id.phone_edit_text);
        universityText = findViewById(R.id.university_edit_text);
        FireStoreBridge fb = new FireStoreBridge("USER");
        String userID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Toast.makeText(this, "this is your first time using the app (please use the same emulator for testing)", Toast.LENGTH_LONG).show();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserFireBaseHolder user = new UserFireBaseHolder();
                user.setId(userID);
                user.setEmail(emailText.getText().toString().trim());
                user.setPhone(phoneText.getText().toString().trim());
                user.setName(userNameText.getText().toString().trim());
                user.setUniversity(universityText.getText().toString().trim());
                fb.updateUser(user);
                Intent intent = new Intent(UserProfileFileActivity.this, MainActivity.class);
                startActivity(intent);


            }


        });

    }



}