package com.example.quantumscan;


import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Authentication {
    /*
    private String userId;
    private FireStoreBridge firebase;
    private Context context;
    private String FILENAME = "CHECK.txt";
    @SuppressLint("HardwareIds")
    public Authentication(Context context){
        this.context = context;
        this.userId = Settings.Secure.getString(this.context.getContentResolver(), Settings.Secure.ANDROID_ID);
        this.firebase = new FireStoreBridge("USER");
    }

    public User accountCreation(String name, String phone, String university, String profilePicture, String email){
        // create a new user
        User user = new User(name, phone, university, profilePicture, email);

        // upload to Firebase
        this.firebase.updateUser(this.userId, user);

        // mark the android device
        saveFile(userId);

        return user;
    }
    private void saveFile(String userId){
        FileOutputStream fo = null;

        try{
            fo = this.context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fo.write(userId.getBytes());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            if (fo != null){
                try {
                    fo.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);

                }
            }
        }
    }
    public boolean checkAuthorization(){
        String FILEPATH = this.context.getFilesDir() + "/" + FILENAME;
        File file = new File(FILEPATH);
        return file.exists();
    }

     */

}


