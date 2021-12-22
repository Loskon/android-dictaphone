package com.loskon.androidprojectdictaphone.request;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.os.Build;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;

import java.util.Map;

/**
 * Регистрация, получение и обработка результатов контракта
 */

public class RequestResults {

    private final ComponentActivity activity;
    private final RequestResultInterface resultInterface;

    private ActivityResultLauncher<String[]> resultLauncherArray; // For Android < 30
    private ActivityResultLauncher<String> resultLauncher; // For Android >= 30

    private static final String[] PERMISSIONS = new String[]
            {RECORD_AUDIO, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};

    public RequestResults(ComponentActivity activity, RequestResultInterface resultInterface) {
        this.activity = activity;
        this.resultInterface = resultInterface;
    }

    // Verification
    public void installingVerificationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            installingVerificationAndroidR();
        } else {
            installingVerification();
        }
    }

    public void installingVerificationAndroidR() {
        resultLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                (ActivityResultCallback<Boolean>) resultInterface::onRequestResult);
    }

    public void installingVerification() {
        resultLauncherArray = activity.registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                this::requestResultsProcessing);
    }

    private void requestResultsProcessing(Map<String, Boolean> permissions) {
        Boolean audio = permissions.get(RECORD_AUDIO);
        Boolean read = permissions.get(READ_EXTERNAL_STORAGE);
        Boolean write = permissions.get(WRITE_EXTERNAL_STORAGE);

        boolean isGranted = false;

        if (audio != null && read != null && write != null) {
            isGranted = (audio && read && write);
        }

        resultInterface.onRequestResult(isGranted);
    }

    // Access
    public boolean hasAccessPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return hasAccessPermissionAndroidR();
        } else {
            return hasAccessPermission();
        }
    }

    public boolean hasAccessPermissionAndroidR() {
        boolean isGrantedAccess = false;

        int audio = ActivityCompat.checkSelfPermission(activity, RECORD_AUDIO);

        if (audio == PERMISSION_GRANTED) {
            isGrantedAccess = true;
        } else {
            requestAccessAudio();
        }

        return isGrantedAccess;
    }

    public boolean hasAccessPermission() {
        boolean isGrantedAccess = false;

        int audio = ActivityCompat.checkSelfPermission(activity, RECORD_AUDIO);
        int read = ActivityCompat.checkSelfPermission(activity, READ_EXTERNAL_STORAGE);
        int write = ActivityCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE);

        int permGranted = PERMISSION_GRANTED;

        if (audio == permGranted && read == permGranted && write == permGranted) {
            isGrantedAccess = true;
        } else {
            requestPermissions();
        }

        return isGrantedAccess;
    }

    // Launcher
    public void requestPermissions() {
        resultLauncherArray.launch(PERMISSIONS);
    }

    public void requestAccessAudio() {
        resultLauncher.launch(RECORD_AUDIO);
    }
}
