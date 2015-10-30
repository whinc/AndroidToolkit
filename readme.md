
### AndroidToolkit

Frequently used classes collection of android

I has published this library on [jcenter][1], you can use in your module's build.gradle like below:

```
dependencies {
    ...
    compile 'com.whinc.util:androidtoolkit:0.1.0'
}
```

### Introduction

#### Log (com.whinc.util.Log)

```
Log.v(TAG, "verbose");
Log.d(TAG, "debug");
Log.i(TAG, "information");
Log.w(TAG, "warning");
Log.e(TAG, "error");

// output with line info.
V/LogActivity﹕ verbose[com.whinc.util.test.LogActivity#onCreate():25]
D/LogActivity﹕ debug[com.whinc.util.test.LogActivity#onCreate():26]
I/LogActivity﹕ information[com.whinc.util.test.LogActivity#onCreate():27]
W/LogActivity﹕ warning[com.whinc.util.test.LogActivity#onCreate():28]
E/LogActivity﹕ error[com.whinc.util.test.LogActivity#onCreate():29]

Log.enablePrintLineInfo(false);
Log.v(TAG, "verbose");
Log.d(TAG, "debug");
Log.i(TAG, "information");
Log.w(TAG, "warning");
Log.e(TAG, "error");

// output with out line info behaves just like android.util.Log
V/LogActivity﹕ verbose
D/LogActivity﹕ debug
I/LogActivity﹕ information
W/LogActivity﹕ warning
E/LogActivity﹕ error

Log.level(Log.LEVEL_I);
Log.v(TAG, "verbose");
Log.d(TAG, "debug");
Log.i(TAG, "information");
Log.w(TAG, "warning");
Log.e(TAG, "error");

// output without line info and has at least specified level
I/LogActivity﹕ information
W/LogActivity﹕ warning
E/LogActivity﹕ error

Log.enable(false);
Log.v(TAG, "verbose");
Log.d(TAG, "debug");
Log.i(TAG, "information");
Log.w(TAG, "warning");
Log.e(TAG, "error");

// output none if log is disable
```

#### CrashHandler

```
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
```

Following is the crash log content:

```
shell@android:/sdcard $ cat crash-1446210011087.log
java.lang.NullPointerException
        at com.whinc.util.test.MainActivity.testCrashHandler(MainActivity.java:83)
        at com.whinc.util.test.MainActivity.onOptionsItemSelected(MainActivity.java:52)
        ...(omission)
        at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:793)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:560)
        at dalvik.system.NativeStart.main(Native Method)
============= Device Info. =============
version name:1.0
version code:1
os: android 4.1.2 (API level 16)
brand:Xiaomi
model:MI 1S
hardware serial:695378c6
============= Custom Info. =============
This is a custom message
```

[1]:https://bintray.com/whinc/maven/androidtoolkit/view
