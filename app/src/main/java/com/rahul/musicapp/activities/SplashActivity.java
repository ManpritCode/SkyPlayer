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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (checkStoragePermission()) {
            startTimer();
        } else {
            requestStoragePermission();
        }
    }

    public void startTimer() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                accessStorage();
            }
        }, 3000);
    }

    private boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                return ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this,
                                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
            } else {
                return ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this,
                                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
            }
        } else {
            return true;
        }
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                // Android 14 and above: Request only READ_MEDIA_AUDIO permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.RECORD_AUDIO},
                        REQUEST_CODE_PERMISSIONS);
            } else {
                // Below Android 14: Request all necessary permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                        REQUEST_CODE_PERMISSIONS);
            }
        }
    }

    private void accessStorage() {
        showSplashScreen();
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
                // Handle permission denial accordingly
            }
        }
    }

    private void showSplashScreen() {
        finish();
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
