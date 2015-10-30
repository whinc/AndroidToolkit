package com.whinc.util.test;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.whinc.util.CrashHandler;
import com.whinc.util.Log;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_log:
                testLog();
                break;
            case R.id.action_crash_handler:
                testCrashHandler();
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * Test {@link com.whinc.util.CrashHandler}
     */
    private void testCrashHandler() {
        String crashLogPath = Environment.getExternalStorageDirectory() + File.separator
                + "crash-" + System.currentTimeMillis() + ".log";
        Context context = this;
        String extraMsg = "This is a custom message";

        CrashHandler.newInstance()
                .setSavePath(crashLogPath)
                .enableCollectDeviceInfo(context)
                .appendMessage(extraMsg)
                .setListener(new CrashHandler.Listener() {
                    @Override
                    public void handleException(String stackTrace) {
                        Log.e(TAG, stackTrace);
                    }
                })
                .install();

        String str = null;
        str.toString();         // Trigger NullPointException
    }


    /**
     * Test {@link Log}
     */
    private void testLog() {
        Log.v(TAG, "verbose");
        Log.d(TAG, "debug");
        Log.i(TAG, "information");
        Log.w(TAG, "warning");
        Log.e(TAG, "error");

        Log.enablePrintLineInfo(false);
        Log.v(TAG, "verbose");
        Log.d(TAG, "debug");
        Log.i(TAG, "information");
        Log.w(TAG, "warning");
        Log.e(TAG, "error");

        Log.level(Log.LEVEL_I);
        Log.v(TAG, "verbose");
        Log.d(TAG, "debug");
        Log.i(TAG, "information");
        Log.w(TAG, "warning");
        Log.e(TAG, "error");

        Log.enable(false);
        Log.v(TAG, "verbose");
        Log.d(TAG, "debug");
        Log.i(TAG, "information");
        Log.w(TAG, "warning");
        Log.e(TAG, "error");
    }
}
