package com.whinc.util;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

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

    /** Default log formatter */
    public static final Formatter DEFAULT_FORMATTER = new InternalFormatter();

	private static boolean sEnable = true;
    private static boolean sPrintLineInfo = true;
    private static int sLevel = LEVEL_V;
    private static Formatter sFormatter = DEFAULT_FORMATTER;

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

	public static void v(String tag, String msg) {
		if (sEnable && sLevel <= LEVEL_V) {
            if (sPrintLineInfo) {
                android.util.Log.v(tag, sFormatter.format(msg, getStackTraceElement(2)));
            } else {
                android.util.Log.v(tag, msg);
            }
		}
	}

    public static void v(String tag, Throwable tr) {
        v(tag, getStackString(tr));
    }

	public static void i(String tag, String msg) {
		if (sEnable && sLevel <= LEVEL_I) {
            if (sPrintLineInfo) {
                android.util.Log.i(tag, sFormatter.format(msg, getStackTraceElement(2)));
            } else {
                android.util.Log.i(tag, msg);
            }
		}
	}

	public static void d(String tag, String msg) {
		if (sEnable && sLevel <= LEVEL_D) {
            if (sPrintLineInfo) {
                android.util.Log.d(tag, sFormatter.format(msg, getStackTraceElement(2)));
            } else {
                android.util.Log.d(tag, msg);
            }
		}
	}

	public static void w(String tag, String msg) {
		if (sEnable && sLevel <= LEVEL_W) {
            if (sPrintLineInfo) {
                android.util.Log.w(tag, sFormatter.format(msg, getStackTraceElement(2)));
            } else {
                android.util.Log.w(tag, msg);
            }
		}
	}

	public static void e(String tag, String msg) {
		if (sEnable && sLevel <= LEVEL_E) {
            if (sPrintLineInfo) {
                android.util.Log.e(tag, sFormatter.format(msg, getStackTraceElement(2)));
            } else {
                android.util.Log.e(tag, msg);
            }
		}
	}

    /**
     * Handy function to get a loggable stack trace from a Throwable
     * @param tr An exception to log
     */
    public static String getStackString(Throwable tr) {
        return android.util.Log.getStackTraceString(tr);
    }

    /**
     * Format log output
     */
    public interface Formatter {
        String format(String msg, StackTraceElement e);
    }

    private static class InternalFormatter implements Formatter{

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
}