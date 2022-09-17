package android.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * https://stackoverflow.com/a/46793567
 */
public class Log {

    private static final String VERBOSE = "V/";
    private static final String DEBUG = "D/";
    private static final String INFO = "I/";
    private static final String WARN = "W/";
    private static final String ERROR = "E/";

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ", Locale.US);

    private static Date getDate() {
        return new Date(System.currentTimeMillis());
    }

    private static void print(PrintStream console, String logLevel, String tag, String msg) {
        console.println(sdf.format(getDate()) + logLevel + ": " + tag + ": " + msg);
    }

    public static int v(String tag, String msg) {
        print(System.out, VERBOSE, tag, msg);
        return 0;
    }

    public static int d(String tag, String msg) {
        print(System.out, DEBUG, tag, msg);
        return 0;
    }

    public static int i(String tag, String msg) {
        print(System.out, INFO, tag, msg);
        return 0;
    }

    public static int w(String tag, String msg) {
        print(System.out, WARN, tag, msg);
        return 0;
    }

    public static int w(String tag, Throwable exception) {
        print(System.out, WARN, tag, exception.toString());
        return 0;
    }

    public static int e(String tag, String msg) {
        print(System.err, ERROR, tag, msg);
        return 0;
    }

    public static int e(String tag, String msg, Throwable tr) {
        print(System.err, ERROR, tag, msg + "\n" + getStackTraceString(tr));
        return 0;
    }

    /**
     * Handy function to get a loggable stack trace from a Throwable
     * @param tr An exception to log
     */
    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }


    // add other methods if required...
}