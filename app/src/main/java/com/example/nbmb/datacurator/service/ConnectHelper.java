package com.example.nbmb.datacurator.service;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration;
import android.util.Log;

import java.util.List;

public class ConnectHelper {
    public static void connectWifi(Context context,String netSSID, String netPass,String netSecurity)
    {
        WifiConfiguration config = new WifiConfiguration();
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        int netID;
        config.SSID = String.format("\"%s\"", netSSID);
        config.preSharedKey = String.format("\"%s\"", netPass);
        netID = manager.addNetwork(config);
        manager.disconnect();
        manager.enableNetwork(netID,true);
        manager.reconnect();
    }
    public static void removeWifi(Context context,String netSSID)
    {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> configs;
        configs = manager.getConfiguredNetworks();
        for (WifiConfiguration config : configs) {
            Log.d("RESULTS","Config SSID: "+ config.SSID);
            Log.d("RESULTS","net SSID: "+ netSSID);
            if (config.SSID.equals("\""+netSSID+"\"")) {
                manager.removeNetwork(config.networkId);
                manager.disableNetwork(config.networkId);
                break;
            }
        }
    }
}
