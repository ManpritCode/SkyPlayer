package com.rahul.musicapp.activities;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.stevenmwesigwa.musicapp.R;

import java.util.Arrays;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    int REQUEST_CODE_PERMISSIONS = 1002;
    private final static String[] permissionsRequired = {Manifest.permission.READ_MEDIA_AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
// Check if the READ_EXTERNAL_STORAGE permission is already granted
        if (checkStoragePermission()) {
            // Permission is granted, you can access the storage
            accessStorage();
        } else {
            // Request storage permission
            requestStoragePermission();
        }
    }

    private boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // For Android 6.0 and above, check if the permission is granted
            return ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        } else {
            // For devices below Android 6.0, permissions are granted at install time
            return true;
        }
    }


    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user why your app needs this permission
                Toast.makeText(this, "Storage permission is required to access your music files", Toast.LENGTH_SHORT).show();
            }

            // Request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSIONS);
        }
    }

    private void accessStorage() {
        // Access the storage here
        Toast.makeText(this, "Accessing storage...", Toast.LENGTH_SHORT).show();
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
                // All requested permissions are granted
                showSplashScreen();
            } else {
                // Permissions were denied, handle accordingly
            }
        }
    }


    /**
     * Shows Splash Screen for a second
     */
    private void showSplashScreen() {

        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @SuppressLint("RtlHardcoded")
    private void displayToastMessage(String toastMessage) {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
        toast.show();
    }


    /*
     * Check if all permissions have been granted or not
     */
    private boolean hasPermissions(final Context context) {
        boolean hasAllPermissions;
        hasAllPermissions = Arrays.stream(SplashActivity.permissionsRequired).noneMatch(perm -> context.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED);
        return hasAllPermissions;
    }
}