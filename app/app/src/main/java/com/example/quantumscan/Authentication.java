package com.example.authtest;

import android.content.Context;
import android.provider.Settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
public class Authentication {
    private String userID;
    private FireBaseConnection firebase;
    private Context context;
    private String FILENAME = "CHECK.txt";
    public Authentication(Context context){
        this.context = context;
        this.userID = Settings.Secure.getString(this.context.getContentResolver(), Settings.Secure.ANDROID_ID);
        this.firebase = new FireBaseConnection("USER");
    }
    private boolean saveFile(String userId){
        FileOutputStream fo = null;

        try{
            fo = this.context.openFileOutput(FILENAME, this.context.MODE_PRIVATE);
            fo.write(userId.getBytes());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            if (fo != null){
                try {
                    fo.close();
                    return true;
                } catch (IOException e) {
                    throw new RuntimeException(e);

                }
            }
        }
        return false;
    }
    public boolean accountCreation(String userId, String name, String profilePicture){
        User user = new User(userId, name, profilePicture);
        // upload to Firebase
        this.firebase.updateUser(userId, user);
        
        // local save file
        saveFile(userId);

        return false;
    }
    public boolean checkAuthorization(){
        String FILEPATH = this.context.getFilesDir() + "/" + FILENAME;
        File file = new File(FILEPATH);
        if (file.exists()){
            return true;
        }
        return false;
    }

}
