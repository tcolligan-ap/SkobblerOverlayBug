package com.apppartner.skobblerbug;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.skobbler.ngx.map.SKAnnotation;
import com.skobbler.ngx.map.SKCoordinateRegion;
import com.skobbler.ngx.map.SKMapCustomPOI;
import com.skobbler.ngx.map.SKMapPOI;
import com.skobbler.ngx.map.SKMapSettings;
import com.skobbler.ngx.map.SKMapSurfaceListener;
import com.skobbler.ngx.map.SKMapSurfaceView;
import com.skobbler.ngx.map.SKMapViewHolder;
import com.skobbler.ngx.map.SKPOICluster;
import com.skobbler.ngx.map.SKScreenPoint;

public class MapActivity extends AppCompatActivity implements SKMapSurfaceListener
{
    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private static final String KEY_CHILD_ACTIVITY = "CHILD_ACTIVITY";

    private SKMapViewHolder mapViewHolder;
    private SKMapSurfaceView mapSurfaceView;
    private boolean didApply;
    private boolean isChildActivity;

    //==============================================================================================
    // Static Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, MapActivity.class);
        context.startActivity(starter);
    }

    public static void startNoDestroy(Context context)
    {
        Intent starter = new Intent(context, MapActivity.class);
        starter.putExtra(KEY_CHILD_ACTIVITY, true);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapViewHolder = (SKMapViewHolder)
                findViewById(R.id.mapViewHolder);
        mapViewHolder.setMapSurfaceListener(this);

        isChildActivity = getIntent().getBooleanExtra(KEY_CHILD_ACTIVITY, false);
        ActionBar actionBar = getSupportActionBar();

        if (isChildActivity && actionBar != null)
        {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar actions click
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mapViewHolder.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mapViewHolder.onPause();
    }

    @Override
    protected void onDestroy()
    {
        if (!isChildActivity)
        {
            SkobblerMapResourceManager.getInstance().destroySKMaps();
        }

        super.onDestroy();
    }

    //==============================================================================================
    // Button Click Methods
    //==============================================================================================

    public void onAnotherMapButtonClicked(View v)
    {
        MapActivity.startNoDestroy(this);
    }

    //==============================================================================================
    // SKMapSurfaceListener Implementation
    //==============================================================================================

    @Override
    public void onActionPan()
    {

    }

    @Override
    public void onActionZoom()
    {

    }

    @Override
    public void onSurfaceCreated(SKMapViewHolder skMapViewHolder)
    {
        mapSurfaceView = skMapViewHolder.getMapSurfaceView();

        if (!didApply)
        {
            didApply = true;
            mapSurfaceView.applySettingsFromFile(SkobblerMapResourceManager.getInstance().getMapCreatorFilePath());
        }

        mapSurfaceView.getMapSettings().setMapRotationEnabled(true);
        mapSurfaceView.getMapSettings().setMapZoomingEnabled(true);
        mapSurfaceView.getMapSettings().setMapPanningEnabled(true);
        mapSurfaceView.getMapSettings().setZoomWithAnchorEnabled(true);
        mapSurfaceView.getMapSettings().setInertiaRotatingEnabled(true);
        mapSurfaceView.getMapSettings().setInertiaZoomingEnabled(true);
        mapSurfaceView.getMapSettings().setInertiaPanningEnabled(true);
        mapSurfaceView.getMapSettings().setCompassShown(true);
        mapSurfaceView.getMapSettings().setFollowPositions(false);
        mapSurfaceView.getMapSettings().setHeadingMode(SKMapSettings.SKHeadingMode.NONE);
    }

    @Override
    public void onMapRegionChanged(SKCoordinateRegion skCoordinateRegion)
    {

    }

    @Override
    public void onMapRegionChangeStarted(SKCoordinateRegion skCoordinateRegion)
    {

    }

    @Override
    public void onMapRegionChangeEnded(SKCoordinateRegion skCoordinateRegion)
    {

    }

    @Override
    public void onDoubleTap(SKScreenPoint skScreenPoint)
    {

    }

    @Override
    public void onSingleTap(SKScreenPoint skScreenPoint)
    {

    }

    @Override
    public void onRotateMap()
    {

    }

    @Override
    public void onLongPress(SKScreenPoint skScreenPoint)
    {

    }

    @Override
    public void onInternetConnectionNeeded()
    {

    }

    @Override
    public void onMapActionDown(SKScreenPoint skScreenPoint)
    {

    }

    @Override
    public void onMapActionUp(SKScreenPoint skScreenPoint)
    {

    }

    @Override
    public void onPOIClusterSelected(SKPOICluster skpoiCluster)
    {

    }

    @Override
    public void onMapPOISelected(SKMapPOI skMapPOI)
    {

    }

    @Override
    public void onAnnotationSelected(SKAnnotation skAnnotation)
    {

    }

    @Override
    public void onCustomPOISelected(SKMapCustomPOI skMapCustomPOI)
    {

    }

    @Override
    public void onCompassSelected()
    {

    }

    @Override
    public void onCurrentPositionSelected()
    {

    }

    @Override
    public void onObjectSelected(int i)
    {

    }

    @Override
    public void onInternationalisationCalled(int i)
    {

    }

    @Override
    public void onBoundingBoxImageRendered(int i)
    {

    }

    @Override
    public void onGLInitializationError(String s)
    {

    }

    @Override
    public void onScreenshotReady(Bitmap bitmap)
    {

    }
}
