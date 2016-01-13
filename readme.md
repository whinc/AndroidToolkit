
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

Add the dependency in your module build.gradle(current `<version>` is `-SNAPSHOT`):

```
dependencies {
    compile 'com.github.whinc:android-toolkit:<version>'
}
```

You can use separated library if you only need some function of it.

* Include only logging functions
```
dependencies {
    compile 'com.github.whinc.android-toolkit:log:<version>'
}
```

* Include other functions except above
```
dependencies {
    compile 'com.github.whinc.android-toolkit:lib:<version>'
}
```


### [QLog][2]

A light weight, easy to use and extensible Log class wrappers the android.util.Log with single class.

#### [CrashHandler][3]

Capture uncaught exception and save to sdcard. You can custom the exception handler, where to save, etc.

[1]:https://bintray.com/whinc/maven/androidtoolkit/view
[2]:./wiki/QLog.md
[3]:./wiki/CrashHandler.md
