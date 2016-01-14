
### AndroidToolkit

A toolkit for android include many useful classes help you develop faster.

### Integration

Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

Add the dependency in your module build.gradle(current `<version>` is `v1.1.4`). You can only use module you need.

* Log module

```
dependencies {
    compile 'com.github.whinc.android-toolkit:toolkit-log:<version>'
}
```

* Util module

```
dependencies {
    compile 'com.github.whinc.android-toolkit:toolkit-util:<version>'
}
```


### [QLog][2]

A light weight, easy to use and extensible Log class wrappers the android.util.Log with single class.

#### [CrashHandler][3]

Capture uncaught exception and save to sdcard. You can custom the exception handler, where to save, etc.

[1]:https://bintray.com/whinc/maven/androidtoolkit/view
[2]:./wiki/QLog.md
[3]:./wiki/CrashHandler.md
