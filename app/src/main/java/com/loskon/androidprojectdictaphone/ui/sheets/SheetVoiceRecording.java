package com.loskon.androidprojectdictaphone.ui.sheets;

import android.content.Context;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.loskon.androidprojectdictaphone.R;
import com.loskon.androidprojectdictaphone.audio.recorder.SoundRecorderControl;
import com.loskon.androidprojectdictaphone.utils.OnSingleClickListener;

/**
 * Окно для записи
 */

public class SheetVoiceRecording {

    private final Context context;
    private BaseSheetDialog dialog;

    private View insertView;
    private MaterialButton btnStopRecording;

    private final SoundRecorderControl recorderControl = new SoundRecorderControl();

    public SheetVoiceRecording(Context context) {
        this.context = context;
        createInsertView();
    }

    private void createInsertView() {
        insertView = View.inflate(context, R.layout.sheet_voice_recording, null);
        btnStopRecording = insertView.findViewById(R.id.stop_recording);
    }

    public void show() {
        dialog = new BaseSheetDialog(context);
        configureDialogViews();
        installHandlers();
        startRecording();
        dialog.show();
    }

    private void configureDialogViews() {
        dialog.setInsertView(insertView);
        dialog.setTextTitle(R.string.sheet_voice__title);
        dialog.setVisibilityBtnDialog(false);
    }

    private void installHandlers() {
        btnStopRecording.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                stopRecording();
                dialog.cancel();
            }
        });

        dialog.setOnCancelListener(dialogInterface -> stopRecording());
    }

    private void stopRecording() {
        recorderControl.stopRecording();
    }

    private void startRecording() {
        recorderControl.startRecording();
    }
}