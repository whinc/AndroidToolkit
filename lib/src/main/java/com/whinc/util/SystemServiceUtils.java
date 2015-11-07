package com.whinc.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * System service utils
 */
public class SystemServiceUtils {

    /**
     * <p>Copy text to system clipboard</p>
     * @param context {@link Context}
     * @param text text will be copied.
     */
    public static void copyToClipboard(@NonNull Context context, CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData.Item item = new ClipData.Item(text);
        ClipData clipData = new ClipData("", new String[]{"text/plain"}, item);
        clipboardManager.setPrimaryClip(clipData);
    }

    /**
     * <p>Schedule activity to be started after specified milliseconds.
     *     <pre>example: scheduleStart(context, 2000, YourActivity.class);</pre></p>
     * @param context {@link Context}
     * @param delayMillis delay milliseconds
     * @param cls the activity will be start
     */
    public static void scheduleStartActivity(@NonNull Context context, long delayMillis, @NonNull Class<?> cls) {
        delayMillis = Math.max(0, delayMillis);
        Intent intent = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                Intent.FLAG_ACTIVITY_NEW_TASK);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + delayMillis, pendingIntent);
    }
}
