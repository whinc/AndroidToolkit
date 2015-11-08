
### AndroidToolkit

Frequently used classes collection of android

I has published this library on [jcenter][1], you can use in your module's build.gradle like below:

```
dependencies {
    ...
    compile 'com.whinc.util:androidtoolkit:0.1.0'
}
```

Below is introduction to these util class.

### Log 

A light weight, easy to use and extensible Log class wrappers the android.util.Log with only a single class.

You can choose to enable or disable specified feature, if you disable all features it acts just same as android.util.Log. 

You can use it with the same way to android.util.Log without no any study cost.

More details reference to [wiki](https://github.com/whinc/AndroidToolkit/wiki/Log)


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
