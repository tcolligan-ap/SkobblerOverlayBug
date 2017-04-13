package com.apppartner.skobblerbug;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity implements SkobblerMapResourceManager.MapResourceListener
{
    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private static final boolean SHOW_MAP_IN_FRAGMENT = false;
    private SkobblerMapResourceManager skobblerMapResourceManager;

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        skobblerMapResourceManager = SkobblerMapResourceManager.getInstance();
        skobblerMapResourceManager.addMapResourceListener(this);
        skobblerMapResourceManager.startMapResourceSetup();
    }

    @Override
    protected void onDestroy()
    {
        skobblerMapResourceManager.removeMapResourceListener(this);
        super.onDestroy();
    }

    //==============================================================================================
    // MapResourceListener Implementation
    //==============================================================================================

    @Override
    public void didFinishLoadingResources()
    {
        if (SHOW_MAP_IN_FRAGMENT)
        {
            startActivity(new Intent(this, MapFragmentActivity.class));
        }
        else
        {
            startActivity(new Intent(this, MapActivity.class));
        }

        finish();
    }

    @Override
    public void errorPreparingResources()
    {
        Toast.makeText(getApplicationContext(),
                R.string.skobbler_error_preparing_map_resources,
                Toast.LENGTH_LONG).show();

        finish();
    }
}
