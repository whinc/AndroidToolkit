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
        Log.i(TAG, "default output format");
        Log.i(TAG, "default output format", new Throwable());

        Log.enablePrintLineInfo(false);
        Log.i(TAG, "disable print line info");

        Log.level(Log.INFO);
        Log.v(TAG, "debug");
        Log.i(TAG, "information");
        Log.e(TAG, "error");

        Log.enable(false);
        Log.v(TAG, "disable log");

        Log.enable(true);
        Log.enablePrintLineInfo(true);
        Log.Formatter formatter = new Log.Formatter() {

            @Override
            public String format(String msg, StackTraceElement e) {
                StringBuilder builder = new StringBuilder();
                String threadInfo = String.format("- Thread:%s\n", Thread.currentThread().getName());
                String lineInfo = String.format("- %s.%s (%s:%d)\n", e.getClassName(), e.getMethodName(), e.getFileName(), e.getLineNumber());
                String msgInfo = String.format("- %s \n", msg);
                builder.append("---------------------------------\n")
                        .append(threadInfo)
                        .append("---------------------------------\n")
                        .append(lineInfo)
                        .append("---------------------------------\n")
                        .append(msgInfo)
                        .append("---------------------------------\n");
                return builder.toString();
            }
        };
        Log.Formatter oldFormatter = Log.setFormatter(formatter);
        Log.i(TAG, "custom formatter");

        testLogVerbose();
        testLogDebug();
        testLogInfo();
        testLogWarn();
        testLogError();
    }

    private void testLogVerbose() {
        Log.restoreDefaultSetting();
        Log.v(TAG, "verbose");
        Log.v(TAG, "verbose", 2);
        Log.v(TAG, "verbose", new Throwable());

        Log.setInterceptor(new Log.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                Log.v(TAG, "intercept " + msg);
                return true;
            }
        });
        Log.v(TAG, "verbose");

        Log.restoreDefaultSetting();
        Log.setInterceptor(new Log.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                Log.v(TAG, "don't intercept " + msg);
                return false;
            }
        });
        Log.v(TAG, "verbose");
    }

    private void testLogDebug() {
        Log.restoreDefaultSetting();
        Log.d(TAG, "debug");
        Log.d(TAG, "debug", 2);
        Log.d(TAG, "debug", new Throwable());

        Log.setInterceptor(new Log.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                Log.d(TAG, "intercept " + msg);
                return true;
            }
        });
        Log.d(TAG, "debug");

        Log.restoreDefaultSetting();
        Log.setInterceptor(new Log.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                Log.d(TAG, "don't intercept " + msg);
                return false;
            }
        });
        Log.d(TAG, "debug");
    }

    private void testLogInfo() {
        Log.restoreDefaultSetting();
        Log.i(TAG, "info");
        Log.i(TAG, "info", 2);
        Log.i(TAG, "info", new Throwable());

        Log.setInterceptor(new Log.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                Log.i(TAG, "intercept " + msg);
                return true;
            }
        });
        Log.i(TAG, "info");

        Log.restoreDefaultSetting();
        Log.setInterceptor(new Log.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                Log.i(TAG, "don't intercept " + msg);
                return false;
            }
        });
        Log.i(TAG, "info");
    }

    private void testLogWarn() {
        Log.restoreDefaultSetting();
        Log.w(TAG, "warn");
        Log.w(TAG, "warn", 2);
        Log.w(TAG, "warn", new Throwable());

        Log.setInterceptor(new Log.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                Log.w(TAG, "intercept " + msg);
                return true;
            }
        });
        Log.w(TAG, "warn");

        Log.restoreDefaultSetting();
        Log.setInterceptor(new Log.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                Log.w(TAG, "don't intercept " + msg);
                return false;
            }
        });
        Log.w(TAG, "warn");
    }

    private void testLogError() {
        Log.restoreDefaultSetting();
        Log.e(TAG, "error");
        Log.e(TAG, "error", 2);
        Log.e(TAG, "error", new Throwable());

        Log.setInterceptor(new Log.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                Log.e(TAG, "intercept " + msg);
                return true;
            }
        });
        Log.e(TAG, "error");

        Log.restoreDefaultSetting();
        Log.setInterceptor(new Log.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                Log.e(TAG, "don't intercept " + msg);
                return false;
            }
        });
        Log.e(TAG, "error");
    }
}
