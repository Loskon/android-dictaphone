package com.loskon.androidprojectdictaphone.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.loskon.androidprojectdictaphone.R;
import com.loskon.androidprojectdictaphone.audio.recorder.CallbackRecordingSuccess;
import com.loskon.androidprojectdictaphone.audio.recorder.SoundRecorderThread;
import com.loskon.androidprojectdictaphone.audio.track.CallbackFailedPlaying;
import com.loskon.androidprojectdictaphone.audio.track.PlayingTrackThread;
import com.loskon.androidprojectdictaphone.other.ColorManager;
import com.loskon.androidprojectdictaphone.request.RequestResultInterface;
import com.loskon.androidprojectdictaphone.request.RequestResults;
import com.loskon.androidprojectdictaphone.ui.sheets.SheetListFiles;
import com.loskon.androidprojectdictaphone.ui.sheets.SheetVoiceRecording;
import com.loskon.androidprojectdictaphone.ui.snackbar.SnackbarControl;
import com.loskon.androidprojectdictaphone.utils.OnSingleClickListener;

/**
 * Основное окно с кнопками
 */

public class MainActivity extends AppCompatActivity
        implements RequestResultInterface, CallbackRecordingSuccess, CallbackFailedPlaying {

    private RequestResults requestResults;

    private ConstraintLayout cstLayout;
    private MaterialButton btnRecord;
    private MaterialButton btnList;

    private boolean isBtnRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorManager.installAppColor(this);
        setContentView(R.layout.activity_main);

        installCallbacks();
        initObjects();
        contractRegistration();
        initViews();
        installHandlers();
    }

    private void installCallbacks() {
        PlayingTrackThread.listenerCallback(this);
        SoundRecorderThread.listenerCallback(this);
    }

    private void initObjects() {
        requestResults = new RequestResults(this, this);
    }

    private void contractRegistration() {
        requestResults.installingVerificationPermissions();
    }

    private void initViews() {
        cstLayout = findViewById(R.id.cst_layout);
        btnRecord = findViewById(R.id.btn_record);
        btnList = findViewById(R.id.btn_list);
    }

    private void installHandlers() {
        btnRecord.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                performClickBtnRecord();
            }
        });

        btnList.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                performClickBtnList();
            }
        });
    }

    private void performClickBtnRecord() {
        SnackbarControl.close();

        if (requestResults.hasAccessPermissions()) {
            showSheetVoiceRecording();
        } else {
            isBtnRecord = true;
        }
    }

    private void performClickBtnList() {
        SnackbarControl.close();

        if (requestResults.hasAccessPermissions()) {
            showSheetListFiles();
        } else {
            isBtnRecord = false;
        }
    }

    private void showSheetVoiceRecording() {
        new SheetVoiceRecording(this).show();
    }

    private void showSheetListFiles() {
        new SheetListFiles(this).show();
    }

    @Override
    public void onRequestResult(boolean isGranted) {
        if (isGranted) {
            if (isBtnRecord) {
                showSheetVoiceRecording();
            } else {
                showSheetListFiles();
            }
        } else {
            makeSnackbar(R.string.sb_no_permissions, false);
        }
    }

    private void makeSnackbar(int stringId, boolean isSuccess) {
        SnackbarControl.make(cstLayout, getString(stringId), isSuccess);
    }

    @Override
    public void onFinishedRecording(boolean isSuccess) {
        int stringId = getMessageForRecording(isSuccess);
        makeSnackbar(stringId, isSuccess);
    }

    private int getMessageForRecording(boolean isSuccess) {
        int stringId;

        if (isSuccess) {
            stringId = R.string.sb_successfully_saved;
        } else {
            stringId = R.string.sb_failed_record;
        }

        return stringId;
    }

    @Override
    public void onFailedPlaying() {
        makeSnackbar(R.string.sb_failed_play, false);
    }
}