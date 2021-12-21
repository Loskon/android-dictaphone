package com.loskon.androidprojectdictaphone.ui.sheets;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.loskon.androidprojectdictaphone.R;

/**
 * Единая форма для нижнего диалогового окна
 */

public class BaseSheetDialog extends BottomSheetDialog {

    private final Context context;
    private final BottomSheetBehavior<FrameLayout> sheetBehavior = getBehavior();

    private TextView tvTitle;
    private MaterialButton btnDialog;
    private LinearLayout linLayout;
    private View contentView;

    public BaseSheetDialog(@NonNull Context context) {
        super(context, R.style.SheetDialogRounded);
        this.context = context;
        //settingsBehavior();
        createContentView();
    }

    private void settingsBehavior() {
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        sheetBehavior.setDraggable(false);
        sheetBehavior.setHideable(false);
    }

    private void createContentView() {
        contentView = View.inflate(context, R.layout.base_sheet_dialog, null);

        tvTitle = contentView.findViewById(R.id.tv_base_dialog_title);
        btnDialog = contentView.findViewById(R.id.btn_base_dialog_cancel);
        linLayout = contentView.findViewById(R.id.container_base_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView);
        //setCancelable(false);
    }


    public void setInsertView(View insertView) {
        //insertView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        if (insertView.getParent() != null) linLayout.removeView(insertView);
        linLayout.addView(insertView);
    }

    public void setTextTitle(int stringId) {
        tvTitle.setText(context.getString(stringId));
    }

    public void setTextBtnDialog(int stringId) {
        btnDialog.setText(context.getString(stringId));
    }

    public MaterialButton getBtnDialog() {
        return btnDialog;
    }
}