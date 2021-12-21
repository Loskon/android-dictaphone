package com.loskon.androidprojectdictaphone.ui.sheets;

import android.content.Context;

import com.loskon.androidprojectdictaphone.R;

/**
 * Окно для записи
 */

public class SheetVoiceRecording {

    private final Context context;
    private BaseSheetDialog dialog;

    public SheetVoiceRecording(Context context) {
        this.context = context;
    }

    public void show() {
        dialog = new BaseSheetDialog(context);

        configureDialogViews();
        configInsertedViews();
        installHandlers();

        dialog.show();
    }

    private void configureDialogViews() {
        //dialog.setInsertView();
        dialog.setTextTitle(R.string.sheet_voice_speak);
        dialog.setTextBtnDialog(R.string.sheet_voice_stop_recording);
    }

    private void configInsertedViews() {

    }

    private void installHandlers() {
        dialog.getBtnDialog().setOnClickListener(view -> dialog.cancel());
    }
}