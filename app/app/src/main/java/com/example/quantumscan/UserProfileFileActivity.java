package com.example.quantumscan;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.quantumscan.databinding.ActivityUserProfileFileBinding;

public class UserProfileFileActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityUserProfileFileBinding binding;
    private EditText userNameText, emailText, phoneText, universityText;
    private Button confirmButton, skipButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserProfileFileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        confirmButton = findViewById(R.id.buttonConfirm);
        userNameText = findViewById(R.id.username_edit_text);
        emailText = findViewById(R.id.email_edit_text);
        phoneText = findViewById(R.id.phone_edit_text);
        universityText = findViewById(R.id.university_edit_text);
        skipButton = findViewById(R.id.skip_button);
        FireStoreBridge fb = new FireStoreBridge("USER");
        String userID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Toast.makeText(this, "this is your first time using the app (please use the same emulator for testing)", Toast.LENGTH_LONG).show();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve trimmed text from all EditText fields, or assign default values if empty
                String userName = userNameText.getText().toString().trim();
                String email = emailText.getText().toString().trim();
                String phone = phoneText.getText().toString().trim();
                String university = universityText.getText().toString().trim();
                if (userName.matches("")) {
                    Toast.makeText(UserProfileFileActivity.this, "user name can not be empty. Please fill in your user name", Toast.LENGTH_LONG).show();
                }
                else {
                    // Create the user with either entered data or default values
                    UserFireBaseHolder user = new UserFireBaseHolder();
                    user.setId(userID); // Assuming userID is previously obtained and valid
                    user.setEmail(email);
                    user.setPhone(phone);
                    user.setName(userName);
                    user.setUniversity(university);

                    // Create the user in Firebase
                    fb.createUser(user);

                    // Navigate to MainActivity
                    Intent intent = new Intent(UserProfileFileActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserFireBaseHolder user = new UserFireBaseHolder();
                user.setId(userID); // Assuming userID is previously obtained and valid
                user.setEmail("email@example.com");
                user.setPhone("*0000000000");
                user.setName("UserName");
                user.setUniversity("Default University");

                // Create the user in Firebase
                fb.createUser(user);

                // Navigate to MainActivity
                Intent intent = new Intent(UserProfileFileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}