package com.loskon.androidprojectdictaphone.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.loskon.androidprojectdictaphone.R;
import com.loskon.androidprojectdictaphone.request.ResultAccessAudio;
import com.loskon.androidprojectdictaphone.request.ResultAccessAudioInterface;
import com.loskon.androidprojectdictaphone.ui.sheets.BaseSheetDialog;
import com.loskon.androidprojectdictaphone.ui.sheets.SheetVoiceRecording;
import com.loskon.androidprojectdictaphone.utils.OnSingleClickListener;

/**
 * Основное окно с кнопками
 */

public class MainActivity extends AppCompatActivity implements ResultAccessAudioInterface {

    private final Context context = this;

    private MaterialButton btnRecord;
    private MaterialButton btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contractRegistration();
        initViews();
        installHandlers();
    }

    private void contractRegistration() {
        ResultAccessAudio.installingVerification(this, this);
    }

    private void initViews() {
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
                new BaseSheetDialog(context).show();
            }
        });
    }

    private void performClickBtnRecord() {
        if (ResultAccessAudio.hasAccessAudio(this)) showSheetVoiceRecording();
    }

    private void showSheetVoiceRecording() {
        new SheetVoiceRecording(context).show();
    }

    @Override
    public void onRequestAudioResult(boolean isGranted) {
        if (isGranted) {
            showSheetVoiceRecording();
        } else {
            Toast.makeText(this, "Нет доступа", Toast.LENGTH_SHORT).show();
        }
    }
}