package com.example.quantumscan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrganizerCreate extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizer_create);

        // Components from this page
        Button buttonReturn = (Button)findViewById(R.id.returnButton);
        Button buttonSave = (Button)findViewById(R.id.saveButton);
        EditText editTextName = (EditText) findViewById(R.id.nameEditText);
        EditText editTextInfo = (EditText) findViewById(R.id.infoEditText);
        EditText editTextID = (EditText) findViewById(R.id.idEditText);

        String nameText;
        String infoText;
        String idText;



        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrganizerCreate.this,OrganizerFragment.class);
                startActivity(intent);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Reading text from EditText
                String nameText = editTextName.getText().toString();
                String infoText = editTextInfo.getText().toString();
                String idText = editTextID.getText().toString();


            }


        });


    }


}
