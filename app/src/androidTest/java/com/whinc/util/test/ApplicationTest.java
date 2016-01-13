package com.whinc.util.test;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.whinc.util.DisplayUtils;
import com.whinc.util.logging.QLog;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private static final String TAG = "ApplicationTest";
    public ApplicationTest() {
        super(Application.class);
    }

    public void testDisplayUtils() {
        QLog.i(TAG, String.valueOf(DisplayUtils.dp2px(getContext(), 100)));
        QLog.i(TAG, String.valueOf(DisplayUtils.px2dp(getContext(), 100)));
    }
}