package com.loskon.androidprojectdictaphone.ui.snackbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;
import com.loskon.androidprojectdictaphone.R;

/**
 * Создание Snackbar с предупреждениями
 */

public class SnackbarControl {

    private static Snackbar snackbar;

    public static void make(ViewGroup parent, String message, boolean isSuccess) {
        snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);

        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundResource(R.drawable.snackbar_background);
        snackbarView.setBackgroundTintList(getSuccessColor(parent.getContext(), isSuccess));
        snackbarView.setOnClickListener(v -> snackbar.dismiss());

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
        params.gravity = Gravity.TOP;
        snackbarView.setLayoutParams(params);

        snackbar.setTextColor(Color.WHITE);
        snackbar.show();
    }

    private static ColorStateList getSuccessColor(Context context, boolean isSuccess) {
        int color;

        if (isSuccess) {
            color = R.color.light_green;
        } else {
            color = R.color.light_red;
        }

        return ColorStateList.valueOf(context.getResources().getColor(color));
    }

    public static void close() {
        if (snackbar != null) {
            snackbar.dismiss();
        }
    }
}
