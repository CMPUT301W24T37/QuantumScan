package com.example.quantumscan;

import android.content.Context;
import android.provider.Settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Authentication {

//    private String userId;
//    private FireStoreBridge firebase;
//    private Context context;
//    private String FILENAME = "CHECK.txt";
//    public Authentication(Context context){
//        this.context = context;
//        this.userId = Settings.Secure.getString(this.context.getContentResolver(), Settings.Secure.ANDROID_ID);
//        this.firebase = new FireStoreBridge("USER");
//    }
//
//    public User accountCreation(User user){
//        // create a new user
//        String userId = user.getId();
//        // upload to Firebase
//        this.firebase.updateUser(user);
//
//        // mark the android device
//        saveFile(userId);
//
//        return user;
//    }
//    private boolean saveFile(String userId){
//        FileOutputStream fo = null;
//
//        try{
//            fo = this.context.openFileOutput(FILENAME, this.context.MODE_PRIVATE);
//            fo.write(userId.getBytes());
//
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } finally{
//            if (fo != null){
//                try {
//                    fo.close();
//                    return true;
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//
//                }
//            }
//        }
//        return false;
//    }
//    public boolean checkAuthorization(){
//        String FILEPATH = this.context.getFilesDir() + "/" + FILENAME;
//        File file = new File(FILEPATH);
//        if (file.exists()){
//            return true;
//        }
//        return false;
//    }
//


}



