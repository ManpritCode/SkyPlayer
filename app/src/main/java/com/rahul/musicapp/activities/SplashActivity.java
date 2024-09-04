package com.rahul.musicapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.stevenmwesigwa.musicapp.R;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    int REQUEST_CODE_PERMISSIONS = 1002;
    int REQUEST_CODE_PERMISSIONS_RECCORD_AUDIO = 1003;
    private final static String[] permissionsRequired = {Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
// Check if the READ_EXTERNAL_STORAGE permission is already granted
        if (checkStoragePermission()) {
            // Permission is granted, you can access the storage

            startTimer();
        } else {
            // Request storage permission
            requestStoragePermission();
        }
    }
    public void startTimer() {
        // Create a new Handler
        Handler handler = new Handler(Looper.getMainLooper());
        // Post a Runnable to be executed after 5 seconds (5000 milliseconds)
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Code to be executed after 5 seconds
                accessStorage();
            }
        }, 3000);  // 5000 milliseconds = 5 seconds
    }
    private boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // For Android 6.0 and above, check if the permission is granted
            return ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;

        } else {
            // For devices below Android 6.0, permissions are granted at install time
            return true;
        }
    }


    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.RECORD_AUDIO)) {
                // Show an explanation to the user why your app needs these permissions
                Toast.makeText(this, "Storage and microphone permissions are required to access your music files and record audio.", Toast.LENGTH_SHORT).show();
            }

            // Request both permissions simultaneously
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                    REQUEST_CODE_PERMISSIONS);
        }
    }

    private void accessStorage() {
        // Access the storage here
        showSplashScreen();
        // Your code to access and play music files goes here
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                showSplashScreen();
            } else {
                // Permissions were denied, handle accordingly
            }
        }
    }

    private void showSplashScreen() {
        finish();
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}