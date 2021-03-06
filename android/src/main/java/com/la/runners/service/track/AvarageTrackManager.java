
package com.la.runners.service.track;

import android.location.Location;

import com.la.runners.util.AppLogger;
import com.la.runners.util.Utils;

public class AvarageTrackManager implements TrackManager {
    
    private static final int DATA_SET_SIZE = 25;

    private StoreManager storeManager;

    private Location[] locations;

    private int index;

    private int dataSetSize;
     
    private boolean firstLocation = Boolean.TRUE;
    
    private double distance;
    
    private double totalDistance;
    
    private double speed;

    private double lastLatitude = 0D;

    private double lastLongitude = 0D;

    private long startTime;

    public AvarageTrackManager(StoreManager storeManager) {
        this(storeManager, DATA_SET_SIZE);
    }

    public AvarageTrackManager(StoreManager storeManager, int dataSetSize) {
        this.storeManager = storeManager;
        this.dataSetSize = dataSetSize;
    }

    @Override
    public void start() {
        index = 0;
        distance = 0;
        //first time I get only one location so is faster
        locations = new Location[1];
        startTime = System.currentTimeMillis();
        storeManager.start();
    }

    @Override
    public void stop() {
        storeManager.stop(System.currentTimeMillis() - startTime, speed, totalDistance);
    }

    @Override
    public void updateLocation(Location location) {
        if(!Utils.Geo.isValidLocation(location)) {
            return;
        }
        AppLogger.debug("updating location : " + location);
        locations[index] = location;
        if (index == dataSetSize - 1 || firstLocation) {
            setPoint(locations);
            firstLocation = Boolean.FALSE;
            locations = new Location[dataSetSize];
            index = 0;
        } else {
            index++;
        }
    }

    private void setPoint(final Location[] locations) {
        double latitude = 0D, longitude = 0D, altitude = 0D;
        for (Location l : locations) {
            latitude += l.getLatitude();
            longitude += l.getLongitude();
            altitude += l.getAltitude();
        }
        int numberOfSample = locations.length;
        double newLatitude = latitude / numberOfSample;
        double newLongitude = longitude / numberOfSample;
        long time = System.currentTimeMillis() - startTime;

        if (lastLongitude == 0D && lastLatitude == 0D) {
            distance = 0D;
            totalDistance = 0D;
        } else {
            distance = Utils.Geo.distance(lastLatitude, lastLongitude, newLatitude, newLongitude);
            totalDistance += distance;
            speed = totalDistance / time; 
        }
        lastLatitude = newLatitude;
        lastLongitude = newLongitude;

        storeManager.trackPoint(newLatitude, newLongitude, altitude / numberOfSample,
                time, System.currentTimeMillis(), speed, distance, totalDistance);
    }


}
