
### AndroidToolkit

A toolkit for android include many useful classes help you develop faster.

### Integration

I has published this library on [jcenter][1], you can use in your module's build.gradle like below:

    dependencies {
        ...
        compile 'com.whinc.util:androidtoolkit:0.1.6'
    }

Below is introduction to these util class.

### [QLog][2]

A light weight, easy to use and extensible Log class wrappers the android.util.Log with single class.

#### [CrashHandler][3]

Capture uncaught exception and save to sdcard. You can custom the exception handler, where to save, etc.

[1]:https://bintray.com/whinc/maven/androidtoolkit/view
[2]:./wiki/QLog.md
[3]:./wiki/CrashHandler.md
