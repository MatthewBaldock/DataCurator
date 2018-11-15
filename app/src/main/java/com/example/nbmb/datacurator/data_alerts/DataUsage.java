package com.example.nbmb.datacurator.data_alerts;

import android.content.Context;
import android.net.TrafficStats;

import com.example.nbmb.datacurator.database.DisableDataHelper;

public class DataUsage {
    long usageSinceBoot;
    long usageSinceLastQuery;
    DisableDataHelper db;

    public DataUsage(Context context) {
        db = new DisableDataHelper(context);
        usageSinceBoot = db.getDataUsageBoot();
        usageSinceLastQuery = db.getDataUsageQuery();
    }

    public long getDataUsage() {
        TrafficStats trafficStats = new TrafficStats();
        usageSinceBoot = db.getDataUsageBoot();
        usageSinceLastQuery = db.getDataUsageQuery();
        long dataUsage = trafficStats.getMobileRxBytes();
        dataUsage += trafficStats.getMobileTxBytes();
        usageSinceLastQuery = dataUsage - usageSinceBoot;
        usageSinceBoot = dataUsage;
        db.setDataUsageBoot(usageSinceBoot);
        db.setDataUsageQuery(usageSinceLastQuery);
        return usageSinceLastQuery;
    }

    public long getUsageSinceBoot() {
        return usageSinceBoot;
    }
}
