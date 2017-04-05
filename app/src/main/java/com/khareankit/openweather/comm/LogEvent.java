package com.khareankit.openweather.comm;

import android.util.Log;
import com.khareankit.openweather.WeatherApplication;

/**
 * Class to handle the log events.
 * @author Ankit Khare
 */
public class LogEvent {
	/**
	 * Creates a log entry if application is debuggable.
	 * @param message Message to put in log.
	 */
	public static void logDebug(String tag, String message) {
		if (WeatherApplication.isDebuggable())
			Log.d(tag, message);
	}

	/**
	 * Creates a log entry if application is debuggable.
	 * @param message Message to put in log.
	 */
	public static void logInfo(String tag, String message) {
		if (WeatherApplication.isDebuggable())
			Log.i(tag, message);
	}

	/**
	 * Creates a log entry if application is debuggable.
	 * @param message Message to put in log.
	 */
	public static void logWarn(String tag, String message) {
		if (WeatherApplication.isDebuggable())
			Log.w(tag, message);
	}

	/**
	 * Creates a log entry if application is debuggable.
	 * @param message Message to put in log.
	 */
	public static void logError(String tag, String message) {
		if (WeatherApplication.isDebuggable())
			Log.e(tag, message);
	}

	/**
	 * Creates a log entry of exception's stack trace.
	 * @param exception Exception to be traced.
	 */
	public static void printStack(Exception exception) {
		if (WeatherApplication.isDebuggable())
			exception.printStackTrace();
	}
}