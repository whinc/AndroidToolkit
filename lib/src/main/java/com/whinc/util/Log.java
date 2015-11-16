package com.whinc.util;

import android.support.annotation.IntDef;

/**
 * Enhanced version of {@link android.util.Log}
 */
public class Log {
    public static final int VERBOSE = android.util.Log.VERBOSE;
    public static final int DEBUG = android.util.Log.DEBUG;
    public static final int INFO = android.util.Log.INFO;
    public static final int WARN = android.util.Log.WARN;
    public static final int ERROR = android.util.Log.ERROR;
    @IntDef({VERBOSE, DEBUG, INFO, WARN, ERROR})
    public @interface Level{}

    /** Default log formatter shows log output in one line. */
    public static final Formatter DEFAULT_FORMATTER = new DefaultFormatter();

	private static boolean sEnable = true;
    private static boolean sPrintLineInfo = true;
    private static int sLevel = VERBOSE;
    private static Formatter sFormatter = DEFAULT_FORMATTER;
    private static Interceptor sInterceptor = null;

	// Disable new instance
	private Log() {}

    /** Enable or disable log output (default enable) */
	public static void enable(boolean b) {
		sEnable = b;
	}

    /** Enable or disable print the line info. */
	public static void enablePrintLineInfo(boolean b) {
        sPrintLineInfo = b;
    }

    /**
     * Set custom formatter
     * @param formatter reference to {@link com.whinc.util.Log.Formatter}
     * @return return previous formatter.
     */
    public static Formatter setFormatter(Formatter formatter) {
        Formatter old = sFormatter;
        sFormatter = formatter == null ? DEFAULT_FORMATTER : formatter;
        return old;
    }

    public static void setInterceptor(Interceptor interceptor) {
        sInterceptor = interceptor;
    }

    public static void restoreDefaultSetting() {
        sEnable = true;
        sPrintLineInfo = true;
        sLevel = VERBOSE;
        sFormatter = DEFAULT_FORMATTER;
        sInterceptor = null;
    }

    /**
     * Set the least log level.
     * @param level reference to {@link com.whinc.util.Log.Level}
     */
    public static void level(@Level int level) {
        sLevel = level;
    }

    /**
     * Get stack trace element object
     * @param depth the depth from caller method to this method.
     * @return
     */
    private static StackTraceElement getStackTraceElement(int depth) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        depth = Math.max(0, Math.min(depth, stackTrace.length - 1));
        return stackTrace[depth];
    }

    /**
     * check if log output is intercepted
     * @param tag
     * @param msg
     * @return return true if log output is intercepted, otherwise return false.
     */
    private static boolean intercept(String tag, String msg) {
        if (sInterceptor == null) {
            return false;
        }

        // store interceptor before call method onIntercept()
        Interceptor prevInterceptor = sInterceptor;
        /* set interceptor to null, this can prevent from recursion call if user call
        Log.v (Log.d, Log.i, etc...) in onIntercept() which will lead to StackOverflow */
        sInterceptor = null;
        boolean r = prevInterceptor.onIntercept(tag, msg);
        // restore interceptor after call method onIntercept()
        sInterceptor = prevInterceptor;
        return r;
    }

    /**
     * <p>Print verbose</p>
     * @param tag
     * @param msg
     * @param start depth from Log.v() to this method
     */
	private static void verbose(String tag, String msg, String extra, int start) {
		if (sEnable && sLevel <= VERBOSE) {
            if (!intercept(tag, msg)) {
                if (sPrintLineInfo) {
                    android.util.Log.v(tag, sFormatter.format(msg, getStackTraceElement(start)));
                } else {
                    android.util.Log.v(tag, msg);
                }
            }
            if (extra != null && !extra.isEmpty()) {
                android.util.Log.v(tag, extra);
            }
        }
	}

    public static void v(String tag, String msg) {
        verbose(tag, msg, null, 3);
    }

    public static void v(String tag, String msg, int callStackDepth) {
        verbose(tag, msg, getCallStack(3, callStackDepth), 3);
    }

    public static void v(String tag, String msg, Throwable tr) {
        verbose(tag, msg, getStackString(tr), 3);
    }

    private static void info(String tag, String msg, String extra, int start) {
        if (sEnable && sLevel <= VERBOSE) {
            if (!intercept(tag, msg)) {
                if (sPrintLineInfo) {
                    android.util.Log.i(tag, sFormatter.format(msg, getStackTraceElement(start)));
                } else {
                    android.util.Log.i(tag, msg);
                }
            }
            if (extra != null && !extra.isEmpty()) {
                android.util.Log.i(tag, extra);
            }
        }
    }

    public static void i(String tag, String msg) {
        info(tag, msg, null, 3);
    }

    public static void i(String tag, String msg, int callStackDepth) {
        info(tag, msg, getCallStack(3, callStackDepth), 3);
    }

    public static void i(String tag, String msg, Throwable tr) {
        info(tag, msg, getStackString(tr), 3);
    }

    private static void debug(String tag, String msg, String extra, int start) {
        if (sEnable && sLevel <= VERBOSE) {
            if (!intercept(tag, msg)) {
                if (sPrintLineInfo) {
                    android.util.Log.d(tag, sFormatter.format(msg, getStackTraceElement(start)));
                } else {
                    android.util.Log.d(tag, msg);
                }
            }
            if (extra != null && !extra.isEmpty()) {
                android.util.Log.d(tag, extra);
            }
        }
    }

    public static void d(String tag, String msg) {
        debug(tag, msg, null, 3);
    }

    public static void d(String tag, String msg, int callStackDepth) {
        debug(tag, msg, getCallStack(3, callStackDepth), 3);
    }

    public static void d(String tag, String msg, Throwable tr) {
        debug(tag, msg, getStackString(tr), 3);
    }

    private static void warn(String tag, String msg, String extra, int start) {
        if (sEnable && sLevel <= VERBOSE) {
            if (!intercept(tag, msg)) {
                if (sPrintLineInfo) {
                    android.util.Log.w(tag, sFormatter.format(msg, getStackTraceElement(start)));
                } else {
                    android.util.Log.w(tag, msg);
                }
            }
            if (extra != null && !extra.isEmpty()) {
                android.util.Log.w(tag, extra);
            }
        }
    }

    public static void w(String tag, String msg) {
        warn(tag, msg, null, 3);
    }

    public static void w(String tag, String msg, int callStackDepth) {
        warn(tag, msg, getCallStack(3, callStackDepth), 3);
    }

    public static void w(String tag, String msg, Throwable tr) {
        warn(tag, msg, getStackString(tr), 3);
    }

    private static void error(String tag, String msg, String extra, int start) {
        if (sEnable && sLevel <= VERBOSE) {
            if (!intercept(tag, msg)) {
                if (sPrintLineInfo) {
                    android.util.Log.e(tag, sFormatter.format(msg, getStackTraceElement(start)));
                } else {
                    android.util.Log.e(tag, msg);
                }
            }
            if (extra != null && !extra.isEmpty()) {
                android.util.Log.e(tag, extra);
            }
        }
    }

    public static void e(String tag, String msg) {
        error(tag, msg, null, 3);
    }

    public static void e(String tag, String msg, int callStackDepth) {
        error(tag, msg, getCallStack(3, callStackDepth), 3);
    }

    public static void e(String tag, String msg, Throwable tr) {
        error(tag, msg, getStackString(tr), 3);
    }

    /**
     * Handy function to get a loggable stack trace from a Throwable
     * @param tr An exception to log
     */
    public static String getStackString(Throwable tr) {
        return android.util.Log.getStackTraceString(tr);
    }

    /**
     * <p>Get call stack</p>
     * @param start call stack start level
     * @param depth call stack depth
     * @return
     */
    public static String getCallStack(int start, int depth) {
        Throwable tr = new Throwable();
        StackTraceElement[] arr = tr.getStackTrace();
        StringBuilder builder = new StringBuilder();
        // index start from '1' exclude current method call stack info.
        for (int i = start; i < (start + depth) && i < arr.length; ++i) {
            StackTraceElement e = arr[i];
            String callInfo = String.format("    %s.%s(%s:%d)",
                    e.getClassName(),
                    e.getMethodName(),
                    e.getFileName(),
                    e.getLineNumber()
            );
            builder.append(callInfo).append("\n");
        }
        return builder.toString();
    }

    /**
     * Format log output
     */
    public interface Formatter {
        String format(String msg, StackTraceElement e);
    }

    private static class DefaultFormatter implements Formatter{

        @Override
        public String format(String msg, StackTraceElement e) {
            return String.format("%s.%s(%s:%d):%s",
                    e.getClassName(),
                    e.getMethodName(),
                    e.getFileName(),
                    e.getLineNumber(),
                    msg
            );
        }
    }

    /**
     * Intercept log output
     */
    public interface Interceptor {
        /**
         * This method will be called every time print log
         * @return return true means user has handled log output and the normal output process will
         * be ignored, otherwise the normal output process will be executed.
         * @param tag log tag
         * @param msg log message
         */
        boolean onIntercept(String tag, String msg);
    }
}