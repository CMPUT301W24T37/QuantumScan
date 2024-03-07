package com.example.quantumscan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class SelectImage {
    private final Activity activity;
    private final ActivityResultLauncher<Intent> activityResultLauncher;

    public SelectImage(Activity activity, ActivityResultLauncher<Intent> activityResultLauncher) {
        this.activity = activity;
        this.activityResultLauncher = activityResultLauncher;
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
    }

}
