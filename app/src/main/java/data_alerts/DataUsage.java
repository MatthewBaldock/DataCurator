package data_alerts;

import android.net.TrafficStats;

public class DataUsage {
    long usageSinceBoot;
    long usageSinceLastQuery;

    public DataUsage() {
        usageSinceBoot = 0;
        usageSinceLastQuery = 0;
    }

    public long getDataUsage() {
        TrafficStats trafficStats = new TrafficStats();
        usageSinceLastQuery = trafficStats.getMobileRxBytes();
        usageSinceLastQuery += trafficStats.getMobileTxBytes();
        long dataUsage = usageSinceLastQuery - usageSinceBoot;
        usageSinceBoot = usageSinceLastQuery;
        return dataUsage;
    }

    public long getUsageSinceBoot() {
        return usageSinceBoot;
    }
}
