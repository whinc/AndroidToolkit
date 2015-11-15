package com.whinc.util;

import android.support.annotation.IntDef;

/**
 * Enhanced version of {@link android.util.Log}
 */
public class Log {
    public static final int LEVEL_V = 2;
    public static final int LEVEL_D = 4;
    public static final int LEVEL_I = 6;
    public static final int LEVEL_W = 8;
    public static final int LEVEL_E = 10;
    @IntDef({LEVEL_V, LEVEL_D, LEVEL_I, LEVEL_W, LEVEL_E})
    public @interface Level{}

    /** Default log formatter shows log output in one line. */
    public static final Formatter DEFAULT_FORMATTER = new DefaultFormatter();

	private static boolean sEnable = true;
    private static boolean sPrintLineInfo = true;
    private static int sLevel = LEVEL_V;
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
        sLevel = LEVEL_V;
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
        return new Throwable().getStackTrace()[depth];
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

	private static void v(String tag, String msg, int depth) {
		if (sEnable && sLevel <= LEVEL_V && !intercept(tag, msg)) {
            if (sPrintLineInfo) {
                android.util.Log.v(tag, sFormatter.format(msg, getStackTraceElement(depth)));
            } else {
                android.util.Log.v(tag, msg);
            }
        }
	}

    public static void v(String tag, String msg) {
        v(tag, msg, 3);
    }

    public static void v(String tag, String msg, Throwable tr) {
        v(tag, msg + "\n" + getStackString(tr), 3);
    }

	private static void i(String tag, String msg, int depth) {
		if (sEnable && sLevel <= LEVEL_I && !intercept(tag, msg)) {
            if (sPrintLineInfo) {
                android.util.Log.i(tag, sFormatter.format(msg, getStackTraceElement(depth)));
            } else {
                android.util.Log.i(tag, msg);
            }
		}
	}

    public static void i(String tag, String msg) {
        i(tag, msg, 3);
    }

    public static void i(String tag, String msg, Throwable tr) {
        i(tag, msg + "\n" + getStackString(tr), 3);
    }

	private static void d(String tag, String msg, int depth) {
		if (sEnable && sLevel <= LEVEL_D && !intercept(tag, msg)) {
            if (sPrintLineInfo) {
                android.util.Log.d(tag, sFormatter.format(msg, getStackTraceElement(depth)));
            } else {
                android.util.Log.d(tag, msg);
            }
		}
	}

    public static void d(String tag, String msg) {
        d(tag, msg, 3);
    }

    public static void d(String tag, String msg, Throwable tr) {
        d(tag, msg + "\n" + getStackString(tr), 3);
    }

	private static void w(String tag, String msg, int depth) {
		if (sEnable && sLevel <= LEVEL_W && !intercept(tag, msg)) {
            if (sPrintLineInfo) {
                android.util.Log.w(tag, sFormatter.format(msg, getStackTraceElement(depth)));
            } else {
                android.util.Log.w(tag, msg);
            }
		}
	}

    public static void w(String tag, String msg) {
        w(tag, msg, 3);
    }

    public static void w(String tag, String msg, Throwable tr) {
        w(tag, msg + "\n" + getStackString(tr), 3);
    }

	private static void e(String tag, String msg, int depth) {
		if (sEnable && sLevel <= LEVEL_E && !intercept(tag, msg)) {
            if (sPrintLineInfo) {
                android.util.Log.e(tag, sFormatter.format(msg, getStackTraceElement(depth)));
            } else {
                android.util.Log.e(tag, msg);
            }
		}
	}

    public static void e(String tag, String msg) {
        e(tag, msg, 3);
    }

    public static void e(String tag, String msg, Throwable tr) {
        e(tag, msg + "\n" + getStackString(tr), 3);
    }

    /**
     * Handy function to get a loggable stack trace from a Throwable
     * @param tr An exception to log
     */
    public static String getStackString(Throwable tr) {
        return android.util.Log.getStackTraceString(tr);
    }

    /**
     * Print call stack info.
     * @param depth call depth
     */
    public static void printCallStack(String tag, int depth) {
        Throwable tr = new Throwable();
        StackTraceElement[] arr = tr.getStackTrace();
        StringBuilder builder = new StringBuilder();
        // index start from '1' exclude current method call stack info.
        for (int i = 1; i <= depth && i < arr.length; ++i) {
            StackTraceElement e = arr[i];
            String callInfo = String.format("%s.%s(%s:%d)",
                    e.getClassName(),
                    e.getMethodName(),
                    e.getFileName(),
                    e.getLineNumber()
            );
            builder.append(callInfo).append("\n");
        }
        android.util.Log.println(android.util.Log.VERBOSE, tag, builder.toString());
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