package com.whinc.util.test;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.whinc.util.CrashHandler;
import com.whinc.util.logging.QLog;

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
                testQLog();
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
        String crashQLogPath = Environment.getExternalStorageDirectory() + File.separator
                + "crash-" + System.currentTimeMillis() + ".QLog";
        Context context = this;
        String extraMsg = "This is a custom message";

        CrashHandler.newInstance()
                .setSavePath(crashQLogPath)
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
     * Test {@link QLog}
     */
    private void testQLog() {
        QLog.i(TAG, "default output format");
        QLog.i(TAG, "default output format", new Throwable());

        QLog.enablePrintLineInfo(false);
        QLog.i(TAG, "disable print line info");

        QLog.enablePrintLineInfo(true);
        QLog.level(QLog.INFO);
        QLog.v(TAG, "verbose");
        QLog.d(TAG, "debug");
        QLog.i(TAG, "information");
        QLog.e(TAG, "error");

        QLog.enable(false);
        QLog.v(TAG, "disable QLog");

        QLog.enable(true);
        QLog.enablePrintLineInfo(true);
        QLog.Formatter formatter = new QLog.Formatter() {

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
        QLog.Formatter oldFormatter = QLog.setFormatter(formatter);
        QLog.i(TAG, "custom formatter");

        testQLogVerbose();
        testQLogDebug();
        testQLogInfo();
        testQLogWarn();
        testQLogError();
    }

    private void testQLogVerbose() {
        QLog.restoreDefaultSetting();
        QLog.v(TAG, "verbose");
        QLog.v(TAG, "verbose", 2);
        QLog.v(TAG, "verbose", new Throwable());

        QLog.setInterceptor(new QLog.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                QLog.v(TAG, "intercept " + msg);
                return true;
            }
        });
        QLog.v(TAG, "verbose");

        QLog.restoreDefaultSetting();
        QLog.setInterceptor(new QLog.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                QLog.v(TAG, "don't intercept " + msg);
                return false;
            }
        });
        QLog.v(TAG, "verbose");
    }

    private void testQLogDebug() {
        QLog.restoreDefaultSetting();
        QLog.d(TAG, "debug");
        QLog.d(TAG, "debug", 2);
        QLog.d(TAG, "debug", new Throwable());

        QLog.setInterceptor(new QLog.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                QLog.d(TAG, "intercept " + msg);
                return true;
            }
        });
        QLog.d(TAG, "debug");

        QLog.restoreDefaultSetting();
        QLog.setInterceptor(new QLog.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                QLog.d(TAG, "don't intercept " + msg);
                return false;
            }
        });
        QLog.d(TAG, "debug");
    }

    private void testQLogInfo() {
        QLog.restoreDefaultSetting();
        QLog.i(TAG, "info");
        QLog.i(TAG, "info", 2);
        QLog.i(TAG, "info", new Throwable());

        QLog.setInterceptor(new QLog.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                QLog.i(TAG, "intercept " + msg);
                return true;
            }
        });
        QLog.i(TAG, "info");

        QLog.restoreDefaultSetting();
        QLog.setInterceptor(new QLog.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                QLog.i(TAG, "don't intercept " + msg);
                return false;
            }
        });
        QLog.i(TAG, "info");
    }

    private void testQLogWarn() {
        QLog.restoreDefaultSetting();
        QLog.w(TAG, "warn");
        QLog.w(TAG, "warn", 2);
        QLog.w(TAG, "warn", new Throwable());

        QLog.setInterceptor(new QLog.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                QLog.w(TAG, "intercept " + msg);
                return true;
            }
        });
        QLog.w(TAG, "warn");

        QLog.restoreDefaultSetting();
        QLog.setInterceptor(new QLog.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                QLog.w(TAG, "don't intercept " + msg);
                return false;
            }
        });
        QLog.w(TAG, "warn");
    }

    private void testQLogError() {
        QLog.restoreDefaultSetting();
        QLog.e(TAG, "error");
        QLog.e(TAG, "error", 2);
        QLog.e(TAG, "error", new Throwable());

        QLog.setInterceptor(new QLog.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                QLog.e(TAG, "intercept " + msg);
                return true;
            }
        });
        QLog.e(TAG, "error");

        QLog.restoreDefaultSetting();
        QLog.setInterceptor(new QLog.Interceptor() {
            @Override
            public boolean onIntercept(String tag, String msg) {
                QLog.e(TAG, "don't intercept " + msg);
                return false;
            }
        });
        QLog.e(TAG, "error");
    }
}
