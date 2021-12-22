package com.loskon.androidprojectdictaphone.other;

import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

/**
 * Настройки цвета для приложения
 */

public class ColorManager {

    public static void installAppColor(Activity activity) {
        installColorStatusBar(activity);
        installColorTask(activity);
    }

    private static void installColorStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flag = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            activity.getWindow().getDecorView().setSystemUiVisibility(flag);
            activity.getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    private static void installColorTask(Activity activity) {
        activity.setTaskDescription(new ActivityManager.TaskDescription(null, null, Color.WHITE));
    }

}
