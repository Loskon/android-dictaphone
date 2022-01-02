package com.loskon.androidprojectdictaphone.ui.sheets;

import android.content.Context;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.loskon.androidprojectdictaphone.R;
import com.loskon.androidprojectdictaphone.audio.recorder.SoundRecorderControl;
import com.loskon.androidprojectdictaphone.utils.OnSingleClick;

/**
 * Окно для записи
 */

public class VoiceRecordingSheetDialog extends BaseSheetDialog {

    private final Context context;

    private MaterialButton btnStopRecording;

    private final SoundRecorderControl recorderControl = new SoundRecorderControl();

    public VoiceRecordingSheetDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void show() {
        configureDialogViews();
        installHandlers();
        startRecording();
        super.show();
    }

    private void configureDialogViews() {
        setTextTitle(R.string.sheet_voice__title);
        setVisibilityBtnDialog(false);
        addViewToDialog();
    }

    private void addViewToDialog() {
        View view = View.inflate(context, R.layout.sheet_voice_recording, null);
        btnStopRecording = view.findViewById(R.id.stop_recording);
        setInsertView(view);
    }

    private void installHandlers() {
        btnStopRecording.setOnClickListener(new OnSingleClick(v -> dialogCancel()));
        setOnCancelListener(dialogInterface -> stopRecording());
    }

    private void dialogCancel() {
        stopRecording();
        cancel();
    }

    private void stopRecording() {
        recorderControl.stopRecording();
    }

    private void startRecording() {
        recorderControl.startRecording();
    }
}