package com.apppartner.skobblerbug;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.skobbler.ngx.SKDeveloperKeyException;
import com.skobbler.ngx.SKMaps;
import com.skobbler.ngx.SKMapsInitializationListener;
import com.skobbler.ngx.map.SKMapSurfaceView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class SkobblerMapResourceManager implements SKMapsInitializationListener
{
    //==============================================================================================
    // Class Properties
    //==============================================================================================

    @SuppressLint("StaticFieldLeak")
    private static SkobblerMapResourceManager instance;
    @SuppressLint("StaticFieldLeak")
    private static Application application;

    private static final String TAG = "MapResourceManager";
    private static final String API_KEY = ""; // TODO: Enter your API Key
    private static final int SKOBBLER_SDK_VERSION = 300; // 3.0.0

    private Handler uiThreadHandler;
    private ArrayList<MapResourceListener> mapResourceListeners;
    private SKMaps skMaps;
    private boolean shouldUpdateSkobblerSdkAssets;
    private String mapResourcesDirPath;
    private String mapCreatorFilePath;
    private boolean didCopyMapResourceFiles;
    private boolean didPrepareMapCreatorFile;
    private boolean didFinishSetup;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void setup(Application application)
    {
        SkobblerMapResourceManager.application = application;
    }

    public static SkobblerMapResourceManager getInstance()
    {
        if (instance == null)
        {
            instance = new SkobblerMapResourceManager();
        }

        return instance;
    }

    //==============================================================================================
    // Constructor
    //==============================================================================================

    public SkobblerMapResourceManager()
    {
        mapResourceListeners = new ArrayList<>();
        skMaps = SKMaps.getInstance();
        uiThreadHandler = new Handler(Looper.getMainLooper());
    }

    //==============================================================================================
    // Class Instance Methods
    //==============================================================================================

    public void addMapResourceListener(MapResourceListener listener)
    {
        removeMapResourceListener(listener);
        mapResourceListeners.add(listener);
    }

    public void removeMapResourceListener(MapResourceListener listener)
    {
        mapResourceListeners.remove(listener);
    }

    public boolean didFinishSetup()
    {
        return didFinishSetup;
    }

    public void destroySKMaps()
    {
        SKMaps.getInstance().destroySKMaps();
        didFinishSetup = false;
        Log.d(TAG, "Destroying SKMaps");
    }

    public String getMapResourcesDirPath()
    {
        return mapResourcesDirPath;
    }

    public String getMapCreatorFilePath()
    {
        return mapCreatorFilePath;
    }

    public void startMapResourceSetup()
    {
        try
        {

            SKMapSurfaceView.preserveGLContext = false;
            shouldUpdateSkobblerSdkAssets = AppCache.getSkobblerSdkVersion() != SKOBBLER_SDK_VERSION;

            skMaps.setApiKey(API_KEY);
            skMaps.initializeSKMaps(application, this);
        }
        catch (SKDeveloperKeyException exception)
        {
            Log.e(TAG, exception.getMessage());
        }
    }

    //==============================================================================================
    // SKMapsInitializationListener Implementation
    //==============================================================================================

    @Override
    public void onLibraryInitialized(boolean isSuccessful)
    {
        if (isSuccessful)
        {
            Log.d(TAG, "Successfully initialized Skobbler map Library");
            Log.d(TAG, "Should Update Skobbler SDK Assets: " + shouldUpdateSkobblerSdkAssets);
            mapResourcesDirPath = skMaps.getMapInitSettings().getMapResourcesPath();

            copyMapResourceFilesInBackground();
            prepareMapCreatorFileInBackground();
        }
        else
        {
            Log.e(TAG, "Failed to initialize Skobbler map Library");
        }
    }

    //==============================================================================================
    // Map Asset Setup Methods
    //==============================================================================================

    private void copyMapResourceFilesInBackground()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                Log.d(TAG, "Started copying map resource files");

                AssetManager assetManager = application.getAssets();

                try
                {
                    String tracksPath = mapResourcesDirPath + "GPXTracks";
                    File tracksDir = new File(tracksPath);
                    boolean resourceAlreadyExists = tracksDir.exists();

                    if (!resourceAlreadyExists || shouldUpdateSkobblerSdkAssets)
                    {
                        if (!resourceAlreadyExists)
                        {
                            tracksDir.mkdirs();
                        }

                        AssetUtils.copyAssetsToFolder(assetManager, "GPXTracks", mapResourcesDirPath + "GPXTracks");
                    }

                    String imagesPath = mapResourcesDirPath + "images";
                    File imagesDir = new File(imagesPath);
                    resourceAlreadyExists = imagesDir.exists();

                    if (!resourceAlreadyExists || shouldUpdateSkobblerSdkAssets)
                    {
                        if (!resourceAlreadyExists)
                        {
                            imagesDir.mkdirs();
                        }
                        AssetUtils.copyAssetsToFolder(assetManager, "images", mapResourcesDirPath + "images");
                    }
                }
                catch (IOException e)
                {
                    Log.e(TAG, "Error copying map resource files");
                    Log.w(TAG, e);
                    notifyErrorPreparingResources();
                    return;
                }

                Log.d(TAG, "Completed copying map resource files");
                didCopyMapResourceFiles = true;
                checkSetupDidFinish();
            }
        }.start();
    }

    private void prepareMapCreatorFileInBackground()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                AssetManager assetManager = application.getAssets();
                Log.d(TAG, "Started copying map creator file");

                final String mapCreatorFolderPath = mapResourcesDirPath + "MapCreator";
                mapCreatorFilePath = mapCreatorFolderPath + "/mapcreatorFile.json";

                try
                {
                    // create the folder where you want to copy the json file
                    final File mapCreatorFolder = new File(mapCreatorFolderPath);

                    boolean resourceAlreadyExists = mapCreatorFolder.exists();
                    if (!resourceAlreadyExists || shouldUpdateSkobblerSdkAssets)
                    {
                        if (!resourceAlreadyExists)
                        {
                            mapCreatorFolder.mkdirs();
                        }

                        AssetUtils.copyAsset(assetManager, "MapCreator", mapCreatorFolderPath, "mapcreatorFile.json");
                    }
                }
                catch (IOException e)
                {
                    Log.e(TAG, "Error copying map creator file");
                    Log.w(TAG, e);
                    notifyErrorPreparingResources();
                    return;
                }

                Log.d(TAG, "Completed copying map creator file");
                didPrepareMapCreatorFile = true;
                checkSetupDidFinish();
            }
        }).start();
    }

    private void notifyErrorPreparingResources()
    {
        // Make sure the listener is called on the main thread
        uiThreadHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                for (MapResourceListener listener : mapResourceListeners)
                {
                    listener.errorPreparingResources();
                }
            }
        });
    }

    private void checkSetupDidFinish()
    {
        if (didCopyMapResourceFiles && didPrepareMapCreatorFile )
        {
            Log.d(TAG, "Resource Setup Completed");
            AppCache.setSkobblerMapSdkVersion(SKOBBLER_SDK_VERSION);
            didFinishSetup = true;

            uiThreadHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    for (MapResourceListener listener : mapResourceListeners)
                    {
                        listener.didFinishLoadingResources();
                    }
                }
            });
        }
    }

    //================================================================================
    // MapResourceListener Interface
    //================================================================================

    interface MapResourceListener
    {
        void didFinishLoadingResources();

        void errorPreparingResources();
    }
}

