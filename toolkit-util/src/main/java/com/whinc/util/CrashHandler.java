package com.whinc.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>This class can capture uncaught exception and save the crash log to
 * specified file. You also can have a listener to the exception handle process, or add custom
 * information to crash log, or collect device information on which crash occurs.
 * </p>
 */
public class CrashHandler {

    private Listener mListener = null;
    private String mSavePath = null;
    private Context mContext = null;    // only be used when need collect device info.
    private String mExtraMessage = null;

    private CrashHandler() {}

    public static CrashHandler newInstance() {
        return new CrashHandler();
    }

    /** Install crash handler. Later if uncaught exception occur the CrashHandler will
     * catch and handle it */
    public void install() {
        final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                if (!handleException(ex) && defaultHandler != null) {
                    defaultHandler.uncaughtException(thread, ex);
                } else {
                    Process.killProcess(Process.myPid());
                    System.exit(1);
                }
            }
        });
    }

    /** Set a listener to listen the exception handle process, when exception is
     * handled the method(s) in the listener will be called */
    public CrashHandler setListener(Listener l) {
        mListener = l;
        return this;
    }

    /** If you set the save path, the stack trace information will
     * be save to the file the parameter specified
     * @param savePath crash log file save path
     * @return
     */
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public CrashHandler setSavePath(String savePath) {
        mSavePath = savePath;
        return this;
    }

    /** If you set the context, the device information will be save to
     * the file the {@code setSavePath(String)} method specified
     * @param context
     * @return
     */
    public CrashHandler enableCollectDeviceInfo(Context context) {
        mContext = context;
        return this;
    }

    /**
     * Append custom string to the crash log.
     * @param msg custom information.
     * @return CrashHandler self
     */
    public CrashHandler appendMessage(String msg) {
        mExtraMessage = msg;
        return this;
    }

    /**
     * Handle exception
     * @param ex {@link Throwable}
     * @return True if user has handled the exception otherwise false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        String stackTraceInfo = collectStackTraceInfo(ex);

        // collect information and save to file
        if (!TextUtils.isEmpty(mSavePath)) {
            StringBuilder builder = new StringBuilder();
            builder.append(stackTraceInfo);
            if (mContext != null) {
                builder.append("============= Device Info. =============\n");
                builder.append(collectDeviceInfo(mContext));
            }
            if (!TextUtils.isEmpty(mExtraMessage)) {
                builder.append("============= Custom Info. =============\n");
                builder.append(mExtraMessage).append('\n');
            }
            save(mSavePath, builder.toString());
        }

        if (mListener != null) {
            mListener.handleException(stackTraceInfo);
        }
        return true;
    }

    /**
     * Save string data to external storage
     * @param path the file path to save data, if file is not exist create it.
     * @param data data need to save
     * @return true if save successful, otherwise false
     */
    private boolean save(String path, String data) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            int lastDotIndex = path.lastIndexOf(File.separator);
            String dirName = path.substring(0, lastDotIndex);
            String fileName = path.substring(lastDotIndex + 1, path.length());

            File dir = new File(dirName);
            if (!dir.exists() && !dir.mkdirs()) {
                return false;
            }

            File file = new File(path);
            try {
                file.createNewFile();
                Writer writer = new PrintWriter(file);
                writer.write(data);
                writer.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        return false;
    }

    private String collectStackTraceInfo(Throwable ex) {
        StringWriter strWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(strWriter);
        ex.printStackTrace(printWriter);
        printWriter.close();
        return strWriter.toString();
    }

    private String collectDeviceInfo(Context context) {

        StringBuilder builder = new StringBuilder();
        Map<String, String> deviceInfo = new LinkedHashMap<String, String>();
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo pkgInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            builder.append("version name:").append(pkgInfo.versionName).append('\n');
            builder.append("version code:").append(pkgInfo.versionCode).append('\n');
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        builder.append(String.format("os: android %s (API level %d)\n",
                Build.VERSION.RELEASE, Build.VERSION.SDK_INT));
        builder.append("brand:").append(Build.BRAND).append('\n');
        builder.append("model:").append(Build.MODEL).append('\n');
        builder.append("hardware serial:").append(Build.SERIAL).append('\n');
        return builder.toString();
    }

    public interface Listener {
        /**
         * <p>This method will be called before App exit and after
         * CrashHandle complete possible work.
         * </p>
         *
         * @param stackTrace stack trace information
         * */
        void handleException(String stackTrace);
    }
}
