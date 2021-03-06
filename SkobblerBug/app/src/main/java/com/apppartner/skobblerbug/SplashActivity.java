package com.apppartner.skobblerbug;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.skobbler.ngx.SKDeveloperKeyException;

public class SplashActivity extends AppCompatActivity implements SkobblerMapResourceManager.MapResourceListener
{
    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private static final String TAG = "SplashActivity";
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

        try
        {
            skobblerMapResourceManager.startMapResourceSetup();
        }
        catch (SKDeveloperKeyException e)
        {
            Log.w(TAG, e);

            new AlertDialog.Builder(this)
                    .setTitle("API KEY ERROR")
                    .setMessage("Check to make sure that you must entered your own Skobbler Developer Key into the source code.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            finish();
                        }
                    })
                    .create()
                    .show();
        }
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
            MapActivity.start(this);
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
