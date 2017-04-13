package com.apppartner.skobblerbug;

import android.app.Application;

public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        AppCache.load(getApplicationContext());
        SkobblerMapResourceManager.setup(this);
    }
}
