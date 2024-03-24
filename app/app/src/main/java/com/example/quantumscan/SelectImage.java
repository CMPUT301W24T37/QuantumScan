package com.example.quantumscan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

/**
 * Helper class to facilitate image selection from the device's gallery.
 */

// REFERENCE CODE LINK: https://github.com/Everyday-Programmer/Upload-Image-to-Firebase-Android/blob/main/app/src/main/java/com/example/uploadimagefirebase/MainActivity.java
public class SelectImage {
    private final Activity activity;
    private final ActivityResultLauncher<Intent> activityResultLauncher;

    /**
     * Constructor for SelectImage.
     * @param activity The activity from which image selection is initiated.
     * @param activityResultLauncher The instance that handles the result.
     */
    public SelectImage(Activity activity, ActivityResultLauncher<Intent> activityResultLauncher) {
        this.activity = activity;
        this.activityResultLauncher = activityResultLauncher;
    }

    /**
     * Triggers an intent to pick an image from the device's gallery.
     * The result will be handled by the ActivityResultLauncher passed during construction.
     */
    public void pickImage() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
    }

}
