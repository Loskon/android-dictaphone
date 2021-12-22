package com.loskon.androidprojectdictaphone.utils;

import android.view.View;

/**
 * Утилита для помощи в работе с вью
 */

public class WidgetUtils {

    public static void setVisibleView(View view, boolean isVisible) {
        int visible;

        if (isVisible) {
            visible = View.VISIBLE;
        } else {
            visible = View.GONE;
        }

        view.setVisibility(visible);
    }

}
