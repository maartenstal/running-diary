
package com.la.runners.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import com.la.runners.R;
import com.la.runners.activity.Preferences;
import com.la.runners.provider.Model;
import com.la.runners.service.track.AvarageTrackManager;
import com.la.runners.service.track.StoreManager;
import com.la.runners.service.track.TrackManager;
import com.la.runners.util.AppLogger;
import com.la.runners.util.Notifier;
import com.la.runners.util.Utils;


public class RunTrackingService extends Service implements LocationListener, StoreManager {

    private static final int MIN_REQUIRED_ACCURACY = 200;
    
    private static final int ONE_MINUTE = 60*1000; 
    
    private static final int FIVE_MIN = ONE_MINUTE * 5;
    
    private TrackManager trackManager;

    private LocationManager locationManager;

    private final Handler handler = new Handler();
    
    private String runId;

    private final TimerTask checkLocationListener = new TimerTask() {
        @Override
        public void run() {
            handler.post(new Runnable() {
                public void run() {
                    AppLogger.debug("Re-registering location listener.");
                    unregisterLocationListener();
                    registerLocationListener();
                }
            });
        }
    };

    private final Timer timer = new Timer();
    
    public static final Intent getIntentAndCheckCondition(Context context) {
        if(((LocationManager)context.getSystemService(LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return new Intent(context, RunTrackingService.class);
        }
        Notifier.toastMessage(context, R.string.error_12);
        return null;
        
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        trackManager = new AvarageTrackManager(this, Preferences.getAvarageSize(getApplicationContext()));
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        registerLocationListener();
        timer.schedule(checkLocationListener, FIVE_MIN, ONE_MINUTE);
        trackManager.start();
    }

    @Override
    public void onDestroy() {
        unregisterLocationListener();
        checkLocationListener.cancel();
        timer.cancel();
        trackManager.stop();
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {
        AppLogger.debug("Location change");
        try {
            if (location == null) {
                AppLogger.warn("Location changed, but location is null.");
                return;
            }
            if (location.getAccuracy() > MIN_REQUIRED_ACCURACY) {
                AppLogger.debug("Not recording. Bad accuracy.");
                return;
            }
            trackManager.updateLocation(location);
        } catch (Error e) {
            AppLogger.error("Error in onLocationChanged", e);
            throw e;
        } catch (RuntimeException e) {
            AppLogger.error("Trapping exception in onLocationChanged", e);
            throw e;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onProviderDisabled(String provider) {
        AppLogger.debug("onProviderDisabled");
    }

    @Override
    public void onProviderEnabled(String provider) {
        AppLogger.debug("onProviderEnabled");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        AppLogger.debug("Provider is enabled");
    }
    
    @Override
    public void trackPoint(double latitude, double longitude, double altitude, long time, long timestamp, 
            double speed, double distance, double totalDistance) {
        ContentValues cv = new ContentValues();
        cv.put(Model.Location.LATITUDE, Utils.Number.e6(latitude));
        cv.put(Model.Location.LONGITUDE, Utils.Number.e6(longitude));
        cv.put(Model.Location.ALTITUDE, Utils.Number.e6(altitude));
        cv.put(Model.Location.SPEED, Utils.Number.e6(speed));
        cv.put(Model.Location.TIME, time);
        cv.put(Model.Location.TIMESTAMP, timestamp);
        cv.put(Model.Location.DISTANCE, (long)distance);
        cv.put(Model.Location.TOTAL_DISTANCE, (long)totalDistance);
        cv.put(Model.Location.RUN_ID, runId);
        getContentResolver().insert(Model.Location.CONTENT_URI, cv);
    }

    @Override
    public void start() {
        ContentValues cv = new ContentValues();
        long time = System.currentTimeMillis();
        cv.put(Model.Run.CREATED, time);
        cv.put(Model.Run.START_DATE, time);
        cv.put(Model.Run.DAY, Utils.Date.day(time));
        cv.put(Model.Run.MONTH, Utils.Date.month(time));
        cv.put(Model.Run.YEAR, Utils.Date.year(time));
        cv.put(Model.Run.MODIFIED, time);
        cv.put(Model.Run.HOUR, Utils.Date.hour(time));
        Uri uri = getContentResolver().insert(Model.Run.CONTENT_URI, cv);
        runId = uri.getLastPathSegment();
    }

    @Override
    public void stop(long duration, double speed, double totalDistance) {
        ContentValues cv = new ContentValues();
        long time = System.currentTimeMillis();
        cv.put(Model.Run.MODIFIED, time);
        cv.put(Model.Run.SPEED, Utils.Number.e6(speed));
        cv.put(Model.Run.DISTANCE, (long)totalDistance);
        cv.put(Model.Run.TIME, duration);
        cv.put(Model.Run.END_DATE, time);
        getContentResolver().update(Model.Run.CONTENT_URI, cv, Model.Run.ID + Model.PARAMETER, new String[]{runId});
    }
    
    private void registerLocationListener() {
        if (locationManager == null) {
            AppLogger.error("Do not have any location manager.");
            return;
        }
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            AppLogger.debug("Location listener registered");
        } catch (RuntimeException e) {
            AppLogger.error("Could not register location listener", e);
        }
    }

    private void unregisterLocationListener() {
        if (locationManager == null) {
            AppLogger.error("Do not have any location manager.");
            return;
        }
        locationManager.removeUpdates(this);
        AppLogger.debug("Location listener unregistered");
    }

}
