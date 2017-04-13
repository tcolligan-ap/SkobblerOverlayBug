package com.apppartner.skobblerbug;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class MapFragment extends Fragment implements SKMapSurfaceListener
{
    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private SKMapViewHolder mapViewHolder;
    private SKMapSurfaceView mapSurfaceView;

    //==============================================================================================
    // Constructor
    //==============================================================================================

    public MapFragment()
    {
        // Required empty public constructor
    }

    public static MapFragment newInstance()
    {
        return new MapFragment();
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapViewHolder = (SKMapViewHolder)
                view.findViewById(R.id.mapViewHolder);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mapViewHolder.setMapSurfaceListener(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mapViewHolder.onResume();

        for (int i = 0; i < 100; i++)
        {
            Log.i("TEST", "TESTER: " + i);
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mapViewHolder.onPause();

        for (int i = 0; i < 100; i++)
        {
            Log.i("TEST", "TESTER: " + i);
        }
    }

    @Override
    public void onDestroy()
    {
        SkobblerMapResourceManager.getInstance().destroySKMaps();
        super.onDestroy();
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
        mapSurfaceView.applySettingsFromFile(SkobblerMapResourceManager.getInstance().getMapCreatorFilePath());
        mapSurfaceView.setZoom(12);

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
