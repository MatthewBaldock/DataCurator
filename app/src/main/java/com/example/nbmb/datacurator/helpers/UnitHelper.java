package com.example.nbmb.datacurator.helpers;

import java.util.concurrent.TimeUnit;

public class UnitHelper {

    public static TimeUnit getTimeUnitFromString(String selection) {
        switch(selection) {
            case "Seconds":
                return TimeUnit.SECONDS;
            case "Minutes":
                return TimeUnit.MINUTES;
            case "Hours":
                return TimeUnit.HOURS;
            case "Days":
                return TimeUnit.DAYS;
            default:
                return TimeUnit.MINUTES;
        }
    }

    public static long getDataUnitFromString(String selection) {
        switch(selection) {
            case "B":
                return 1;
            case "KB":
                return 1000;
            case "MB":
                return 1000000;
            case "GB":
                return 1000000000;
            default:
                return 1000000;
        }
    }
}
