package com.example.martin.gerelefrigo;

import android.app.Application;

/**
 * Created by Martin on 20/04/2016.
 */

public class MyApplication extends Application {
    private StorageService storageService;

    @Override
    public void onCreate() {
        super.onCreate();
        storageService = new StorageServiceImpl(this);
    }

    public StorageService getStorageService() {
        return storageService;
    }
}
