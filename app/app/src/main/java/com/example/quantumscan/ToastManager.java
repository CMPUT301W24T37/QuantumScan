package com.example.quantumscan;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class ToastManager {
    private static ToastManager instance;
    private Toast lastToast;
    private ArrayList<String> messageList;
    private ToastManager() {

        this.messageList = new ArrayList<>();
    }

    public static synchronized ToastManager getInstance() {
        if (instance == null) {
            instance = new ToastManager();
        }
        return instance;
    }

    public void showToast(Context context, String message) {
        if(messageList.size()==0 || !messageList.contains(message)) {
            messageList.add(message);
            lastToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            lastToast.show();
        }
    }

    public void removeMessage(String message){
        messageList.remove(message);
    }
}