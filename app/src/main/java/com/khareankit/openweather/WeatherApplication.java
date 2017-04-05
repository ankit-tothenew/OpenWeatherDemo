package com.khareankit.openweather;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * This application class is used to define application level context and items.
 *
 * @Author by Ankit Khare.
 */
public class WeatherApplication extends Application {

    /**
     * true when application is signed with debug keystore, false otherwise.
     */
    private static boolean isDebuggable;
    private Context mContext;

    /**
     * @return true if application is in debug mode, false otherwise.
     */
    public static boolean isDebuggable() {
        return isDebuggable;
    }

    /**
     * Set the value of {@link #isDebuggable} by checking flags in application info.
     */
    private void checkDebuggable() {
        isDebuggable = ((getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
    }

    public Context getWeatherContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        checkDebuggable();
        setContext(this);
    }
}
