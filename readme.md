
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

`Log` (com.whinc.util.Log)

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

[1]:https://bintray.com/whinc/maven/androidtoolkit/view
