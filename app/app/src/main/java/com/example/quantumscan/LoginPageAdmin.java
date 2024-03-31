package com.example.quantumscan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPageAdmin extends AppCompatActivity {
    String enterKey;
    String encryptionKey;
    EditText editText;
    Button buttonLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login_screen);
        editText = (EditText)findViewById(R.id.editTextText);
        buttonLogin = (Button)findViewById(R.id.button);
        encryptionKey = "12345";




        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                enterKey = editText.getText().toString();
                if(enterKey.equals(encryptionKey)){
                    Intent intent = new Intent(LoginPageAdmin.this, AdminHomepage.class);
                    System.out.println("True, running page");
                    startActivity(intent);

                }

            }
        });

    }
}
