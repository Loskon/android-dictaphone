package com.loskon.androidprojectdictaphone.request;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;

/**
 * Регистрация, получение и обработка результатов контракта
 */

public class ResultAccessAudio {

    private static ActivityResultLauncher<String> resultLauncher;

    private static final String AUDIO = Manifest.permission.RECORD_AUDIO;
    private static final int GRANTED = PackageManager.PERMISSION_GRANTED;

    public static void installingVerification(
            ComponentActivity activity,
            ResultAccessAudioInterface resultInterface
    ) {
        resultLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                (ActivityResultCallback<Boolean>) resultInterface::onRequestAudioResult);
    }

    public static boolean hasAccessAudio(Context context) {
        boolean isGrantedAccess = false;
        int audio = ActivityCompat.checkSelfPermission(context, AUDIO);

        if (audio == GRANTED) {
            isGrantedAccess = true;
        } else {
            requestAccessAudio();
        }

        return isGrantedAccess;
    }

    public static void requestAccessAudio() {
        resultLauncher.launch(Manifest.permission.RECORD_AUDIO);
    }
}
