package com.apppartner.skobblerbug;

import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class AssetUtils
{
    /**
     * Copies files from assets to destination folder
     */
    static void copyAssetsToFolder(AssetManager assetManager,
                                          String sourceFolder,
                                          String destinationFolder) throws IOException
    {
        final String[] assets = assetManager.list(sourceFolder);

        final File destFolderFile = new File(destinationFolder);
        if (!destFolderFile.exists())
        {
            destFolderFile.mkdirs();
        }

        copyAsset(assetManager, sourceFolder, destinationFolder, assets);
    }

    /**
     * Copies files from assets to destination folder
     */
    static void copyAsset(AssetManager assetManager,
                                 String sourceFolder,
                                 String destinationFolder,
                                 String... assetsNames) throws IOException
    {

        for (String assetName : assetsNames)
        {
            OutputStream destinationStream = new FileOutputStream(new File(destinationFolder + "/" + assetName));
            String[] files = assetManager.list(sourceFolder + "/" + assetName);
            if (files == null || files.length == 0)
            {

                InputStream asset = assetManager.open(sourceFolder + "/" + assetName);

                try
                {
                    byte[] buffer = new byte[0x1000];
                    int read;
                    while ((read = asset.read(buffer)) != -1)
                    {
                        destinationStream.write(buffer, 0, read);
                    }
                }
                finally
                {
                    asset.close();
                    destinationStream.close();
                }
            }
        }
    }

}

