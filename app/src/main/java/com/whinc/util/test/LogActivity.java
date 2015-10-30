package com.whinc.util.test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.whinc.util.Log;

public class LogActivity extends AppCompatActivity {
    private static final String TAG = LogActivity.class.getSimpleName();

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LogActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
