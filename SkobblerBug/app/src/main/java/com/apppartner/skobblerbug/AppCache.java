package com.apppartner.skobblerbug;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

class AppCache
{
    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private static final String KEY_SKOBBLER_MAP_SDK_VERSION = "KEY_SKOBBLER_MAP_SDK_VERSION";

    @SuppressLint("StaticFieldLeak")
    private static Context applicationContext;
    private static int skobblerSdkVersion;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void load(Context context)
    {
        AppCache.applicationContext = context.getApplicationContext();

        SharedPreferences prefs = applicationContext.getSharedPreferences(BuildConfig.APPLICATION_ID,
                Context.MODE_PRIVATE);

        if (prefs != null)
        {
            skobblerSdkVersion = prefs.getInt(KEY_SKOBBLER_MAP_SDK_VERSION, 0);
        }
    }

    public static int getSkobblerSdkVersion()
    {
        return skobblerSdkVersion;
    }

    public static void setSkobblerMapSdkVersion(int skobblerSdkVersion)
    {
        AppCache.skobblerSdkVersion = skobblerSdkVersion;
        SharedPreferences prefs = applicationContext.getSharedPreferences(BuildConfig.APPLICATION_ID,
                Context.MODE_PRIVATE);

        if (prefs != null)
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(KEY_SKOBBLER_MAP_SDK_VERSION, skobblerSdkVersion);
            editor.apply();
        }
    }
}
