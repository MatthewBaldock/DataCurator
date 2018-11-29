package com.example.nbmb.datacurator.data_alerts;

public class DataAlert {
    public int alertNum;
    public long dataLimit;
    public long timePeriod;
    public String dataUnit;
    public String timeUnit;

    public DataAlert(int alertNum, long dataLimit, long timePeriod, String dataUnit, String timeUnit) {
        this.alertNum = alertNum;
        this.dataLimit = dataLimit;
        this.timePeriod = timePeriod;
        this.dataUnit = dataUnit;
        this.timeUnit = timeUnit;
    }
}
