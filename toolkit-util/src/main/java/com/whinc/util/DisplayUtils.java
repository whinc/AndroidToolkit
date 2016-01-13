package com.whinc.util;

import android.content.Context;

/**
 * Created by whinc on 2015/12/15.
 */
public class DisplayUtils {

    public static float dp2px(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static float px2dp(Context context, float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }
}
