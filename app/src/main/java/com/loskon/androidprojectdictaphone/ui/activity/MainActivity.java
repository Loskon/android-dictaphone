package com.loskon.androidprojectdictaphone.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.loskon.androidprojectdictaphone.R;
import com.loskon.androidprojectdictaphone.ui.sheets.BaseSheetDialog;
import com.loskon.androidprojectdictaphone.ui.sheets.SheetVoiceRecording;
import com.loskon.androidprojectdictaphone.utils.OnSingleClickListener;

/**
 * Основное окно с кнопками
 */

public class MainActivity extends AppCompatActivity {

    private final Context context = this;

    private MaterialButton btnRecord;
    private MaterialButton btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        installHandlers();
    }

    private void initViews() {
        btnRecord = findViewById(R.id.btn_record);
        btnList = findViewById(R.id.btn_list);
    }

    private void installHandlers() {
        btnRecord.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                new SheetVoiceRecording(context).show();
            }
        });

        btnList.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                new BaseSheetDialog(context).show();
            }
        });
    }
}