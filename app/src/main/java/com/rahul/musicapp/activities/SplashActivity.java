package com.rahul.musicapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

checkAndRequestPermissions();
    }

    private void checkAndRequestPermissions() {
        // Check if the device is running Android 13 or later
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                // Request both READ_MEDIA_AUDIO and RECORD_AUDIO permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.RECORD_AUDIO},
                        REQUEST_CODE_PERMISSIONS);
            } else {
                // Permissions already granted, proceed with your app logic
                showSplashScreen();
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // Request both RECORD_AUDIO and READ_EXTERNAL_STORAGE permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_PERMISSIONS);
            } else {
                // Permissions already granted, proceed with your app logic
                showSplashScreen();
            }
        } else {
            showSplashScreen();
            // Older versions, handle permissions as needed (READ_EXTERNAL_STORAGE, RECORD_AUDIO)
            // You might want to handle these cases separately depending on your app's requirements.
        }
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