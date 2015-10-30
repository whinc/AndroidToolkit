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

	private static boolean sEnable = true;
    private static boolean sPrintLineInfo = true;
    private static int sLevel = LEVEL_V;

	// 禁止实例化
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
     * Set the least log level.
     * @param level reference to {@link com.whinc.util.Log.Level}
     */
    public static void level(@Level int level) {
        sLevel = level;
    }

	/** Get stack information
	 * @param depth the depth to this method
	 * @return line info. format "[PackageName.ClassName#MethodName().LineNumber]"
	 * */
	protected static String buildStackTraceInfo(int depth) {
		StringBuilder builder = new StringBuilder();
		Throwable throwable = new Throwable();
		StackTraceElement e = throwable.getStackTrace()[depth];
		builder.append('[')
				.append(e.getClassName())
				.append('#')
				.append(e.getMethodName() + "()")
				.append(':')
				.append(e.getLineNumber())
				.append(']');
		return builder.toString();
	}

	protected static String buildStackTraceInfo() {
		return buildStackTraceInfo(3);		// 调用Logger类的方法的深度为3
	}

	public static void v(String tag, String msg) {
		if (sEnable && sLevel <= LEVEL_V) {
            if (sPrintLineInfo) {
                android.util.Log.v(tag, msg + buildStackTraceInfo());
            } else {
                android.util.Log.v(tag, msg);
            }
		}
	}

	public static void i(String tag, String msg) {
		if (sEnable && sLevel <= LEVEL_I) {
            if (sPrintLineInfo) {
                android.util.Log.i(tag, msg + buildStackTraceInfo());
            } else {
                android.util.Log.i(tag, msg);
            }
		}
	}

	public static void d(String tag, String msg) {
		if (sEnable && sLevel <= LEVEL_D) {
            if (sPrintLineInfo) {
                android.util.Log.d(tag, msg + buildStackTraceInfo());
            } else {
                android.util.Log.d(tag, msg);
            }
		}
	}

	public static void w(String tag, String msg) {
		if (sEnable && sLevel <= LEVEL_W) {
            if (sPrintLineInfo) {
                android.util.Log.w(tag, msg + buildStackTraceInfo());
            } else {
                android.util.Log.w(tag, msg);
            }
		}
	}

	public static void e(String tag, String msg) {
		if (sEnable && sLevel <= LEVEL_E) {
            if (sPrintLineInfo) {
                android.util.Log.e(tag, msg + buildStackTraceInfo());
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
}