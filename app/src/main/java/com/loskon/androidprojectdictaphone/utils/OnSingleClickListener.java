package com.loskon.androidprojectdictaphone.utils;

import android.os.SystemClock;
import android.view.View;

/**
 * Позволяет убрать двойной клик
 */

public abstract class OnSingleClickListener implements View.OnClickListener {

    private long lastClickTime = 0;

    @Override
    public final void onClick(View view) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 600) {
            return;
        }

        lastClickTime = SystemClock.elapsedRealtime();

        onSingleClick(view);
    }

    public abstract void onSingleClick(View view);
}
