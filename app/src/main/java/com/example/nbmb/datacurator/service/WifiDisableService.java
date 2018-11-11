package com.example.nbmb.datacurator.service;

import android.app.IntentService;

import android.content.Intent;

public class WifiDisableService extends IntentService {
    public WifiDisableService(String name)
    {
        super(name);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
